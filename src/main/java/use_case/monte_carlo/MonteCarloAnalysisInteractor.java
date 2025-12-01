package use_case.monte_carlo;

import data_access.FileMonteCarloDataAccess;
import data_access.FileUserDataAccessObject;
import entities.Stock;
import entities.StockMetrics;
import entities.monte_carlo.MonteCarloSimulation;
import entities.monte_carlo.MonteCarloSimulator;
import data_access.StooqStockDataAccess;
import entities.PriceBar;
import entities.StatisticsCalculator;
import interface_adapter.monte_carlo.MonteCarloPresenter;
import view.monte_carlo.SwingMonteCarloView;

import java.util.List;

/**
 * The Interactor (Use Case) for the Monte Carlo simulation.
 * It is responsible for orchestrating the steps: data fetching, metrics calculation,
 * simulation, and finally delivering the raw results via the Output Boundary.
 */
public class MonteCarloAnalysisInteractor implements MonteCarloInputBoundary {

    private final StooqStockDataAccess dataAccess;
    private final MonteCarloSimulator simulator;
    private final StatisticsCalculator metricsCalculator;
    private final MonteCarloOutputBoundary outputBoundary;
    private final FileMonteCarloDataAccess montecarloDataAccess;
    /**
     * Interactor dependencies are injected.
     */
    public MonteCarloAnalysisInteractor(StooqStockDataAccess dataAccess,
                                        MonteCarloSimulator simulator,
                                        StatisticsCalculator metricsCalculator,
                                        MonteCarloOutputBoundary outputBoundary,
                                        FileMonteCarloDataAccess montecarloDataAccess) {
        this.dataAccess = dataAccess;
        this.simulator = simulator;
        this.metricsCalculator = metricsCalculator;
        this.outputBoundary = outputBoundary;
        this.montecarloDataAccess = montecarloDataAccess;
    }

    /**
     * Executes the Monte Carlo simulation use case, triggered by the Controller.
     */
    @Override
    public void execute(MonteCarloInputData inputData) {
        try {
            String ticker = inputData.getTicker();
            double horizonYears = inputData.getHorizonYears();
            int nSteps = inputData.getNSteps();
            int nPaths = inputData.getNPaths();

            final Stock stock = dataAccess.getDailySeries(ticker, 400);
            List<PriceBar> priceHistory = stock.getPriceHistory();
            if (priceHistory.size() < 2) {
                outputBoundary.presentError("Analysis failed: Not enough data (" + priceHistory.size() + " days).");
                return;
            }

            // Assuming latest price is at index 0 based on the 'singlestock' interactor's logic
            double initialPrice = priceHistory.get(0).getClose();

            // 2. Metrics Calculation
            StockMetrics metrics = metricsCalculator.calculateMetrics(priceHistory);
            double muAnnual = metrics.getAnnualReturn();
            double sigmaAnnual = metrics.getAnnualVolatility();

            // 3. Monte Carlo Simulation
            double[][] simulatedPaths = simulator.simulate(
                    initialPrice,
                    muAnnual,
                    sigmaAnnual,
                    horizonYears,
                    nSteps,
                    nPaths
            );

            // 4. Calculate Final Metric
            double meanTerminalPrice = simulator.meanTerminal(simulatedPaths);

            // 5. Present Results (Deliver raw results via the Output Boundary)
            MonteCarloOutputData output = new MonteCarloOutputData(
                    simulatedPaths,
                    initialPrice,
                    meanTerminalPrice
            );

            MonteCarloSimulation simulationResult = new MonteCarloSimulation(
                    inputData.getTicker(),
                    inputData.getHorizonYears(),
                    inputData.getNPaths(),
                    inputData.getNSteps(),
                    muAnnual,
                    sigmaAnnual,
                    meanTerminalPrice,
                    simulatedPaths
            );

            montecarloDataAccess.saveSimulation(simulationResult);

            outputBoundary.presentSuccess(output);

        } catch (Exception e) {
            outputBoundary.presentError("An analysis error occurred: " + e.getMessage());
        }
    }

    public void executeHistoryRetrieval(String ticker) {
        SwingMonteCarloView view = new SwingMonteCarloView();
        MonteCarloPresenter presenter = new MonteCarloPresenter(view);
        try {
            // 1. Call the persistence gateway to fetch the data
            List<MonteCarloSimulation> history =
                    montecarloDataAccess.getHistory(ticker); // <-- Calls your FileMonteCarloDataAccess


            if (history.isEmpty()) {
                presenter.presentError("No saved simulations found for " + ticker + ".");
                return;
            }

            // 2. Format and present the history
            // NOTE: You'll need to update your Presenter/OutputData to handle this list.
            presenter.presentHistorySuccess(history);

        } catch (Exception e) {
            presenter.presentError("Error retrieving history: " + e.getMessage());
        }
    }
}
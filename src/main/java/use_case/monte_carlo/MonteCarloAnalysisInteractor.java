package use_case.monte_carlo;

import entities.StockMetrics;
import entities.monte_carlo.MonteCarloSimulator;
import data_access.StooqStockDataAccess;
import entities.PriceBar;
import entities.StatisticsCalculator;

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

    /**
     * Interactor dependencies are injected.
     */
    public MonteCarloAnalysisInteractor(StooqStockDataAccess dataAccess,
                                        MonteCarloSimulator simulator,
                                        StatisticsCalculator metricsCalculator,
                                        MonteCarloOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.simulator = simulator;
        this.metricsCalculator = metricsCalculator;
        this.outputBoundary = outputBoundary;
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

            // 1. Data Fetching (Use 400 days history as per your previous interactor)
            List<PriceBar> priceHistory = dataAccess.getDailySeries(ticker, 400);

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

            outputBoundary.presentSuccess(output);

        } catch (Exception e) {
            outputBoundary.presentError("An analysis error occurred: " + e.getMessage());
        }
    }
}
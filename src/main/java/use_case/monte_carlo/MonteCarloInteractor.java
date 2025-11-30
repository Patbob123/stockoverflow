package use_case.monte_carlo;

import entities.MonteCarloSimulator;
import entities.Stock;
import use_case.APIDataAccessInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MonteCarloInteractor implements MonteCarloInputBoundary {
    private final APIDataAccessInterface apiDataAccess;
    private final MonteCarloOutputBoundary presenter;
    private final MonteCarloSimulator simulator;

    public MonteCarloInteractor(APIDataAccessInterface apiDataAccess,
                                MonteCarloOutputBoundary presenter) {
        this.apiDataAccess = apiDataAccess;
        this.presenter = presenter;
        this.simulator = new MonteCarloSimulator();
    }

    @Override
    public void execute(MonteCarloInputData inputData) {
        Stock stock = apiDataAccess.getStock(inputData.getTicker());

        if (stock == null || stock.getHistoricalPrices().isEmpty()) {
            presenter.prepareFailView("No data found for ticker: " + inputData.getTicker());
            return;
        }

        Map<java.time.LocalDate, Double> history = stock.getHistoricalPrices();
        List<Double> prices = new ArrayList<>(history.values());

        if (prices.size() < 30) {
            presenter.prepareFailView("Insufficient historical data for Monte Carlo.");
            return;
        }

        double[] returns = new double[prices.size() - 1];
        for (int i = 0; i < prices.size() - 1; i++) {
            // Log Return = ln(P_t / P_{t-1})
            double pToday = prices.get(i + 1);
            double pYesterday = prices.get(i);
            returns[i] = Math.log(pToday / pYesterday);
        }

        double sum = 0.0;
        for (double r : returns) sum += r;
        double meanDaily = sum / returns.length;

        double sumSq = 0.0;
        for (double r : returns) sumSq += Math.pow(r - meanDaily, 2);
        double stdDevDaily = Math.sqrt(sumSq / (returns.length - 1));

        double muAnnual = meanDaily * 252;
        double sigmaAnnual = stdDevDaily * Math.sqrt(252);
        double s0 = stock.getClose();

        try {
            double[][] paths = simulator.simulate(
                    s0,
                    muAnnual,
                    sigmaAnnual,
                    (double) inputData.getTimeHorizon() / 252.0, // Horizon in years
                    inputData.getTimeHorizon(), // Steps
                    inputData.getSimulationCount() // Paths
            );

            MonteCarloOutputData outputData = new MonteCarloOutputData(stock.getTicker(), paths, s0);
            presenter.prepareSuccessView(outputData);

        } catch (Exception e) {
            presenter.prepareFailView("Simulation Error: " + e.getMessage());
        }
    }
}

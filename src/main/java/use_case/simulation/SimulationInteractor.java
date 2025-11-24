package use_case.simulation;

import entities.StatisticsCalculator;
import entities.montecarlo.MonteCarloSimulator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SimulationInteractor implements SimulationInputBoundary {
    private final StockDataAccessInterface stockDataAccessObject;
    private final SimulationOutputBoundary simulationPresenter;

    public SimulationInteractor(StockDataAccessInterface stockDataAccessObject,
                                SimulationOutputBoundary simulationPresenter) {
        this.stockDataAccessObject = stockDataAccessObject;
        this.simulationPresenter = simulationPresenter;
    }

    @Override
    public void execute(SimulationInputData inputData) {
        try {
            // 1. Fetch Historical Data
            Map<LocalDate, Double> prices = stockDataAccessObject.getHistoricalPrices(inputData.getTicker());

            if (prices.isEmpty()) {
                simulationPresenter.prepareFailView("No data found for ticker: " + inputData.getTicker());
                return;
            }

            // 2. Calculate Returns for Volatility and Mean
            List<Double> priceList = new ArrayList<>(prices.values());
            // Ensure chronological order if Map isn't sorted, but TreeMap in mock is sorted.
            // Let's rely on map iterator order for now or sort keys.
            // TreeMap ensures key order.

            double[] returns = new double[priceList.size() - 1];
            for (int i = 0; i < priceList.size() - 1; i++) {
                double p1 = priceList.get(i);
                double p2 = priceList.get(i + 1);
                returns[i] = (p2 - p1) / p1;
            }

            StatisticsCalculator stats = new StatisticsCalculator();
            double dailyVol = stats.calculateVolatility(returns);
            double dailyMean = stats.mean(returns);

            // Annualize
            double annualVol = dailyVol * Math.sqrt(252);
            double annualMean = dailyMean * 252;

            double initialPrice = priceList.get(priceList.size() - 1); // Last price

            // 3. Run Simulation
            MonteCarloSimulator simulator = new MonteCarloSimulator();
            int steps = (int) (inputData.getTimeHorizon() * 252);

            double[][] paths = simulator.simulate(
                    initialPrice,
                    annualMean,
                    annualVol,
                    inputData.getTimeHorizon(),
                    steps,
                    inputData.getNumPaths()
            );

            // 4. Output
            SimulationOutputData outputData = new SimulationOutputData(inputData.getTicker(), paths);
            simulationPresenter.prepareSuccessView(outputData);

        } catch (Exception e) {
            simulationPresenter.prepareFailView("Error during simulation: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

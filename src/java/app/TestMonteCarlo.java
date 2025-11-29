package app;

import entities.montecarlo.MonteCarloSimulator;
import view.MonteCarloChartView;

public class TestMonteCarlo {
    public static void main(String[] args) throws Exception {
        // --- Fake inputs until the API provides real ones ---
        double initialPrice = 150.0;
        double annualReturn = 0.10;
        double volatility = 0.20;
        double horizon = 1.0;        // in years
        int steps = 252;  // number of trading days in a year wihtout weekend and holidays
        int paths = 100000;

        // --- Run simulation ---
        MonteCarloSimulator mc = new MonteCarloSimulator();
        double[][] pathsArray = mc.simulate(
                initialPrice, annualReturn, volatility, horizon, steps, paths);

        // --- Visualize ---
        MonteCarloChartView.showPaths(pathsArray, 100, "Sample Monte Carlo Paths");
    }
}

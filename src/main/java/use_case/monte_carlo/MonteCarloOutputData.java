package use_case.monte_carlo;

public class MonteCarloOutputData {
    private final String ticker;
    private final double[][] simulationPaths;
    private final double initialPrice;

    public MonteCarloOutputData(String ticker, double[][] simulationPaths, double initialPrice) {
        this.ticker = ticker;
        this.simulationPaths = simulationPaths;
        this.initialPrice = initialPrice;
    }

    public String getTicker() { return ticker; }
    public double[][] getSimulationPaths() { return simulationPaths; }
    public double getInitialPrice() { return initialPrice; }
}
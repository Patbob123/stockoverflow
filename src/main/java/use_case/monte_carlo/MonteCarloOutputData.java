package use_case.monte_carlo;

public class MonteCarloOutputData {
    private final double[][] paths;
    private final double initialPrice;
    private final double meanTerminalPrice;

    public MonteCarloOutputData(double[][] paths, double initialPrice, double meanTerminalPrice) {
        this.paths = paths;
        this.initialPrice = initialPrice;
        this.meanTerminalPrice = meanTerminalPrice;
    }

    public double[][] getPaths() { return paths; }
    public double getInitialPrice() { return initialPrice; }
    public double getMeanTerminalPrice() { return meanTerminalPrice; }
}
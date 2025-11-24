package use_case.simulation;

public class SimulationOutputData {
    private final double[][] paths;
    private final String ticker;

    public SimulationOutputData(String ticker, double[][] paths) {
        this.ticker = ticker;
        this.paths = paths;
    }

    public double[][] getPaths() {
        return paths;
    }

    public String getTicker() {
        return ticker;
    }
}

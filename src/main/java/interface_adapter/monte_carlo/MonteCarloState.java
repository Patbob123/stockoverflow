package interface_adapter.monte_carlo;

public class MonteCarloState {
    private String ticker = "";
    private double[][] simulationPaths = null;
    private String error = null;

    public MonteCarloState(MonteCarloState copy) {
        this.ticker = copy.ticker;
        this.simulationPaths = copy.simulationPaths;
        this.error = copy.error;
    }
    public MonteCarloState() {}

    public String getTicker() { return ticker; }
    public void setTicker(String ticker) { this.ticker = ticker; }
    public double[][] getSimulationPaths() { return simulationPaths; }
    public void setSimulationPaths(double[][] simulationPaths) { this.simulationPaths = simulationPaths; }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}
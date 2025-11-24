package interface_adapter.simulation;

public class SimulationState {
    private String ticker = "";
    private String error = null;

    // Simulation results
    private double[][] simulationPaths = null;

    public SimulationState(SimulationState copy) {
        ticker = copy.ticker;
        error = copy.error;
        simulationPaths = copy.simulationPaths;
    }

    public SimulationState() {}

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public double[][] getSimulationPaths() {
        return simulationPaths;
    }

    public void setSimulationPaths(double[][] simulationPaths) {
        this.simulationPaths = simulationPaths;
    }
}

package use_case.simulation;

public class SimulationInputData {
    private final String ticker;
    private final int numPaths;
    private final double timeHorizon; // in years

    public SimulationInputData(String ticker, int numPaths, double timeHorizon) {
        this.ticker = ticker;
        this.numPaths = numPaths;
        this.timeHorizon = timeHorizon;
    }

    public String getTicker() {
        return ticker;
    }

    public int getNumPaths() {
        return numPaths;
    }

    public double getTimeHorizon() {
        return timeHorizon;
    }
}

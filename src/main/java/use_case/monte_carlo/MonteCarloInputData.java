package use_case.monte_carlo;

public class MonteCarloInputData {
    private final String ticker;
    private final int simulationCount;
    private final int timeHorizon;

    public MonteCarloInputData(String ticker, int simulationCount, int timeHorizon) {
        this.ticker = ticker;
        this.simulationCount = simulationCount;
        this.timeHorizon = timeHorizon;
    }

    public String getTicker() { return ticker; }
    public int getSimulationCount() { return simulationCount; }
    public int getTimeHorizon() { return timeHorizon; }
}
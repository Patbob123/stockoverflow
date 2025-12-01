package use_case.historical_simulation;

public class HistoricalSimulationOutputData {
    private final double initialValue;
    private final double currentValue;
    private final double totalReturn; // Percentage (e.g., 0.15 for 15%)
    private final String startDateStr;

    public HistoricalSimulationOutputData(double initialValue, double currentValue, double totalReturn, String startDateStr) {
        this.initialValue = initialValue;
        this.currentValue = currentValue;
        this.totalReturn = totalReturn;
        this.startDateStr = startDateStr;
    }

    public double getInitialValue() { return initialValue; }
    public double getCurrentValue() { return currentValue; }
    public double getTotalReturn() { return totalReturn; }
    public String getStartDateStr() { return startDateStr; }
}

package interface_adapter.singlestock;

public class SingleStockState {
    private String currentTicker = "";
    private String currentRiskFree = "0.045";
    private String report = "Enter ticker and click Analyze...";
    private String errorMessage = null;

    public SingleStockState() {}

    public SingleStockState(SingleStockState copy) {
        this.currentTicker = copy.currentTicker;
        this.currentRiskFree = copy.currentRiskFree;
        this.report = copy.report;
        this.errorMessage = copy.errorMessage;
    }

    // --- Getters ---
    public String getCurrentTicker() {
        return currentTicker;
    }

    public String getCurrentRiskFree() {
        return currentRiskFree;
    }

    public String getReport() {
        return report;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    // --- Setters ---
    public void setCurrentTicker(String currentTicker) {
        this.currentTicker = currentTicker;
    }

    public void setCurrentRiskFree(String currentRiskFree) {
        this.currentRiskFree = currentRiskFree;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
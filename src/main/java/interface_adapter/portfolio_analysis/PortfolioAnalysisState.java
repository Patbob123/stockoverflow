package interface_adapter.portfolio_analysis;

public class PortfolioAnalysisState {
    private String analysisResult = "";
    private String error = null;

    public PortfolioAnalysisState() {}
    public PortfolioAnalysisState(PortfolioAnalysisState copy) {
        this.analysisResult = copy.analysisResult;
        this.error = copy.error;
    }

    public String getAnalysisResult() { return analysisResult; }
    public void setAnalysisResult(String analysisResult) { this.analysisResult = analysisResult; }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}
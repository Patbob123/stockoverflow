package use_case.portfolio_analysis;

public interface PortfolioAnalysisOutputBoundary {
    void prepareSuccessView(PortfolioAnalysisOutputData outputData);
    void prepareFailView(String error);
}

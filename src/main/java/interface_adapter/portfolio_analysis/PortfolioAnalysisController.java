package interface_adapter.portfolio_analysis;

import use_case.portfolio_analysis.PortfolioAnalysisInputBoundary;
import use_case.portfolio_analysis.PortfolioAnalysisInputData;

public class PortfolioAnalysisController {
    final PortfolioAnalysisInputBoundary interactor;

    public PortfolioAnalysisController(PortfolioAnalysisInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String username, String portfolioName) {
        PortfolioAnalysisInputData inputData = new PortfolioAnalysisInputData(username, portfolioName);
        interactor.execute(inputData);
    }
}
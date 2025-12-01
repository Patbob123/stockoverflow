package use_case.create_portfolio;

import use_case.OutputBoundary;

public class CreatePortfolioOutputData {
    private final String portfolioName;
    private final boolean useCaseFailed;

    public CreatePortfolioOutputData(String portfolioName, boolean useCaseFailed) {
        this.portfolioName = portfolioName;
        this.useCaseFailed = useCaseFailed;
    }

    public String getPortfolioName() { return portfolioName; }
}
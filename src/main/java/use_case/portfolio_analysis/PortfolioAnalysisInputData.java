package use_case.portfolio_analysis;

public class PortfolioAnalysisInputData {
    private final String username;
    private final String portfolioName;

    public PortfolioAnalysisInputData(String username, String portfolioName) {
        this.username = username;
        this.portfolioName = portfolioName;
    }

    public String getUsername() { return username; }
    public String getPortfolioName() { return portfolioName; }
}
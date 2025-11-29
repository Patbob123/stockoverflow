package use_case.create_portfolio;

public class CreatePortfolioInputData {
    final private String portfolioName;
    final private String username;

    public CreatePortfolioInputData(String username, String portfolioName) {
        this.username = username;
        this.portfolioName = portfolioName;
    }

    public String getPortfolioName() { return portfolioName; }
    public String getUsername() { return username; }
}
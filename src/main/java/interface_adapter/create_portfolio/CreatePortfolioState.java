package interface_adapter.create_portfolio;

public class CreatePortfolioState {
    private String portfolioName = "";
    private String error = null;
    private String username = "";

    public CreatePortfolioState(CreatePortfolioState copy) {
        this.portfolioName = copy.portfolioName;
        this.error = copy.error;
        this.username = copy.username;
    }

    public CreatePortfolioState() {}

    public String getPortfolioName() { return portfolioName; }
    public void setPortfolioName(String portfolioName) { this.portfolioName = portfolioName; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    // 新增 Getter 和 Setter
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
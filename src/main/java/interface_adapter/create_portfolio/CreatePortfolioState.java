package interface_adapter.create_portfolio;

import java.util.ArrayList;
import java.util.List;

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

    private List<String> portfolioNames = new ArrayList<>();

    public List<String> getPortfolioNames() { return portfolioNames; }
    public void setPortfolioNames(List<String> names) { this.portfolioNames = names; }

    public String getPortfolioName() { return portfolioName; }
    public void setPortfolioName(String portfolioName) { this.portfolioName = portfolioName; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
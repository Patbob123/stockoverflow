package use_case.add_stock;

import java.util.List;

public class AddStockInputData {
    private final String username;
    private final String portfolioName;
    private final List<String> tickers;

    public AddStockInputData(String username, String portfolioName, List<String> tickers) {
        this.username = username;
        this.portfolioName = portfolioName;
        this.tickers = tickers;
    }

    public String getUsername() { return username; }
    public String getPortfolioName() { return portfolioName; }
    public List<String> getTickers() { return tickers; }
}
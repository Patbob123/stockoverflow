package use_case.remove_stock;

public class RemoveStockInputData {
    private final String username;
    private final String portfolioName;
    private final String ticker;

    public RemoveStockInputData(String username, String portfolioName, String ticker) {
        this.username = username;
        this.portfolioName = portfolioName;
        this.ticker = ticker;
    }

    public String getUsername() { return username; }
    public String getPortfolioName() { return portfolioName; }
    public String getTicker() { return ticker; }
}
package interface_adapter.add_stock;

import java.util.ArrayList;
import java.util.List;

public class AddStockState {
    private String portfolioName = "";
    private String username = ""; // Needed for Interactor
    private String searchInput = "";
    private List<String> searchResults = new ArrayList<>(); // Tickers shown in the menu
    private String message = null;

    public AddStockState(AddStockState copy) {
        this.portfolioName = copy.portfolioName;
        this.username = copy.username;
        this.searchInput = copy.searchInput;
        this.searchResults = new ArrayList<>(copy.searchResults);
        this.message = copy.message;
    }

    public AddStockState() {}

    public String getPortfolioName() { return portfolioName; }
    public void setPortfolioName(String portfolioName) { this.portfolioName = portfolioName; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getSearchInput() { return searchInput; }
    public void setSearchInput(String searchInput) { this.searchInput = searchInput; }

    public List<String> getSearchResults() { return searchResults; }
    public void addSearchResult(String ticker) {
        if(!searchResults.contains(ticker)) searchResults.add(ticker);
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}

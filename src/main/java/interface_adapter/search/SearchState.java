package interface_adapter.search;

import java.util.ArrayList;
import java.util.List;

public class SearchState {
    private String ticker = "";
    private String error = null;
    private List<String> searchResults = new ArrayList<>();

    public SearchState(SearchState copy) {
        this.ticker = copy.ticker;
        this.error = copy.error;
        this.searchResults = new ArrayList<>(copy.searchResults);
    }

    public SearchState() {}

    public String getTicker() { return ticker; }
    public void setTicker(String ticker) { this.ticker = ticker; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public List<String> getSearchResults() { return searchResults; }
    public void setSearchResults(List<String> searchResults) { this.searchResults = searchResults; }
}
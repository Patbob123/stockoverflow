package interface_adapter.search;

public class SearchState {
    private String ticker = "";
    private String error = null;

    public SearchState(SearchState copy) {
        this.ticker = copy.ticker;
        this.error = copy.error;
    }

    public SearchState() {}

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

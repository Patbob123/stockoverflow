package interface_adapter.show_graph;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ShowGraphState {
    private Map<String, Map<LocalDate, Double>> stockData = new HashMap<>();
    private String errorMessage = null;
    private String previousViewName = "main menu";

    public ShowGraphState(ShowGraphState copy) {
        this.stockData = new HashMap<>(copy.stockData);
        this.errorMessage = copy.errorMessage;
        this.previousViewName = copy.previousViewName;
    }

    public ShowGraphState() {}

    public Map<String, Map<LocalDate, Double>> getStockData() {
        return stockData;
    }

    public void setStockData(Map<String, Map<LocalDate, Double>> stockData) {
        this.stockData = stockData;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getPreviousViewName() {
        return previousViewName;
    }

    public void setPreviousViewName(String previousViewName) {
        this.previousViewName = previousViewName;
    }
}
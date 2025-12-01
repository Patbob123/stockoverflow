package use_case.show_graph;

import java.time.LocalDate;
import java.util.Map;

public class ShowGraphOutputData {
    private final Map<String, Map<LocalDate, Double>> stockData;
    private final String errorMessage;
    private final String previousViewName;

    public ShowGraphOutputData(Map<String, Map<LocalDate, Double>> stockData, String errorMessage, String previousViewName) {
        this.stockData = stockData;
        this.errorMessage = errorMessage;
        this.previousViewName = previousViewName;
    }

    public Map<String, Map<LocalDate, Double>> getStockData() {
        return stockData;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getPreviousViewName() {
        return previousViewName;
    }
}
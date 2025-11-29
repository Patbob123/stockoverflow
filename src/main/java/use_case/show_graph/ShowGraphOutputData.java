package use_case.show_graph;

import java.time.LocalDate;
import java.util.Map;

public class ShowGraphOutputData {
    // Map<Ticker, Map<Date, Price>>
    private final Map<String, Map<LocalDate, Double>> stockData;
    private final boolean useCaseFailed;
    private final String errorMessage;

    public ShowGraphOutputData(Map<String, Map<LocalDate, Double>> stockData, boolean useCaseFailed, String errorMessage) {
        this.stockData = stockData;
        this.useCaseFailed = useCaseFailed;
        this.errorMessage = errorMessage;
    }

    public Map<String, Map<LocalDate, Double>> getStockData() {
        return stockData;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
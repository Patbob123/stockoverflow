package entities;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents a financial stock entity.
 * Stores current price data and historical price data for analysis and graphing.
 */
public class Stock {

    private final String ticker;
    private String name;
    private LocalDate lastUpdated;

    // Current/Latest market data
    private Double open;
    private Double close;
    private Double high;
    private Double low;

    // Historical data: Date -> Close Price (Sorted by Date)
    private Map<LocalDate, Double> historicalPrices;

    public Stock(String ticker, String name) {
        this.ticker = ticker;
        this.name = name;
        // TreeMap ensures dates are always sorted, which is crucial for graphing (User Story 5)
        this.historicalPrices = new TreeMap<>();
    }

    // --- Getters and Setters ---

    public String getTicker() {
        return ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public Double getOpen() { return open; }
    public Double getClose() { return close; }
    public Double getHigh() { return high; }
    public Double getLow() { return low; }

    public Map<LocalDate, Double> getHistoricalPrices() {
        return historicalPrices;
    }

    public void setHistoricalPrices(Map<LocalDate, Double> historicalPrices) {
        // Wrap in TreeMap to guarantee sorting if the input wasn't sorted
        this.historicalPrices = new TreeMap<>(historicalPrices);
    }

    /**
     * Updates the latest quote information for the stock.
     * Also appends the close price to the historical record.
     */
    public void updateQuote(LocalDate date, double open, double close, double high, double low) {
        this.lastUpdated = date;
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;

        if (this.historicalPrices != null) {
            this.historicalPrices.put(date, close);
        }
    }

    /**
     * Calculates the daily price range (High - Low).
     */
    public double getDailyRange() {
        checkQuoteLoaded();
        return high - low;
    }

    /**
     * Calculates the daily return percentage ((Close - Open) / Open).
     */
    public double getDailyReturn() {
        checkQuoteLoaded();
        return (close - open) / open;
    }

    private void checkQuoteLoaded() {
        if (open == null || close == null || high == null || low == null) {
            throw new IllegalStateException("Stock data not loaded for ticker: " + ticker);
        }
    }
}
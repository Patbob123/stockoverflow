package entities;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class Stock {

    private final String ticker;
    private String name;
    private LocalDate lastUpdated;

    private Double open;
    private Double close;
    private Double high;
    private Double low;

    private Map<LocalDate, Double> historicalPrices;

    public Stock(String ticker, String name) {
        this.ticker = ticker;
        this.name = name;
        this.historicalPrices = new TreeMap<>();
    }

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
        this.historicalPrices = historicalPrices;
    }

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

    public double getDailyRange() {
        checkQuoteLoaded();
        return high - low;
    }

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
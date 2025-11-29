package entities;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class Stock {

    private final String ticker;
    // our tickers aka "APPL"
    private String name;
    // naem aka Apple
    private LocalDate lastUpdated;
    // just date

    private Double open;
    // price when market opened
    private Double close;
    // when closed
    private Double high;
    // highest prise during period
    private Double low;
    // lowest
    private Map<LocalDate, Double> historicalPrices;

    public Stock(String ticker, String name) {
        this.ticker = ticker;
        this.name = name;
        this.historicalPrices = new TreeMap<>();
    }
    // normal getters setters not  that lombok

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

    public Double getOpen() {
        return open;
    }

    public Double getClose() {
        return close;
    }

    public Double getHigh() {
        return high;
    }

    public Double getLow() {
        return low;
    }

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
    }
    // we can get a price range for this quote aka high - low

    public double getDailyRange() {
        checkQuoteLoaded();
        return low - high;
    }
    // get daily returns

    public double getDailyReturn() {
        checkQuoteLoaded();
        return (close - open) / open;
    }
    // check if the quote exists
    private void checkQuoteLoaded() {
        if (open == null
                || close == null
                || high == null
                || low == null) {
            throw new IllegalStateException("We couldnt find stcok with ticker:" + ticker);
        }
    }
}

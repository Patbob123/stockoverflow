package entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;

public class Stock {

    private final String ticker;
    // our tickers aka "APPL"
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

    @Getter
    @Setter
    private List<PriceBar> priceHistory;
    private LocalTime time;

    public Stock(String ticker) {
        this.ticker = ticker;
    }
    public Stock(String ticker, List<PriceBar> priceHistory) {
        this.time = LocalTime.now();
        this.ticker = ticker;
        this.priceHistory = priceHistory;
    }
    // normal getters setters not  that lombok

    public String getTicker() {
        return ticker;
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

    /**
    * Gdasdasd.
    */
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

/** Im not sure about this lombok
 import lombok.AllArgsConstructor;
 import lombok.Getter;
 import lombok.Setter;

 import java.util.Date;
 import javax.swing.*;

 @Getter
 @Setter
 //@AllArgsConstructor
 public class Stock {
 private String ticker;
 private String name;
 private Date date;
 private ImageIcon image;
 private double high;
 private double low;
 private double close;

 check push
 */
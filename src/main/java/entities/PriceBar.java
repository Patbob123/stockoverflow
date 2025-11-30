
package entities;

import java.time.LocalDate;

public class PriceBar {

    private final LocalDate date;
    private final double open;
    private final double high;
    private final double low;
    private final double close;
    private final long volume;

    public PriceBar(LocalDate date,
                    double open,
                    double high,
                    double low,
                    double close,
                    long volume) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public long getVolume() {
        return volume;
    }
}

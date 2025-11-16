package entities;

import java.time.LocalDate;

public class PricePoint {
    private final LocalDate date;
    private final double close;

    public PricePoint(LocalDate date, double close){
        this.date = date;
        this.close = close;
    }

    public LocalDate getDate() {return date;}
    public double getClose() {return close;}
}


package main.java.use_case.singlestock;

import entities.PriceBar;

import java.util.List;

public interface StockPriceDataAccessInterface {
    List<PriceBar> getDailySeries(String ticker, int maxDays);
}
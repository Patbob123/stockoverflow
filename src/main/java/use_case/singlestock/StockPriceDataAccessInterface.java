
package use_case.singlestock;

import entities.PriceBar;
import entities.Stock;

import java.util.List;

public interface StockPriceDataAccessInterface {
    Stock getDailySeries(String ticker, int maxDays);
}
package data_access;

import entities.PriceBar;
import entities.Stock;
import use_case.singlestock.StockPriceDataAccessInterface;


import java.util.ArrayList;
import java.util.List;

public class CombinedStockPriceDataAccess implements StockPriceDataAccessInterface {

    private final StockPriceDataAccessInterface primary;   // Stooq
    private final StockPriceDataAccessInterface fallback;  // Alpha Vantage

    public CombinedStockPriceDataAccess(StockPriceDataAccessInterface primary,
                                        StockPriceDataAccessInterface fallback) {
        this.primary = primary;
        this.fallback = fallback;
    }

    @Override
    public Stock getDailySeries(String ticker, int maxDays) {
        Stock first;
        try {
            first = primary.getDailySeries(ticker, maxDays);
        } catch (RuntimeException ex) {
            first = new Stock(ticker);
        }

        if (!first.getPriceHistory().isEmpty()) {
            return first;
        }

        // primary gave nothing → try fallback
        final Stock second;
        try {
            second = fallback.getDailySeries(ticker, maxDays);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Both price providers failed for " + ticker + ": " + ex.getMessage(), ex);
        }

        return second;
    }
}

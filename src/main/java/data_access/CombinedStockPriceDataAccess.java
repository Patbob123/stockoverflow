package data_access;

import entities.PriceBar;
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
    public List<PriceBar> getDailySeries(String ticker, int maxDays) {
        List<PriceBar> first;
        try {
            first = primary.getDailySeries(ticker, maxDays);
        } catch (RuntimeException ex) {
            first = List.of();
        }

        if (!first.isEmpty()) {
            return new ArrayList<>(first);
        }

        // primary gave nothing → try fallback
        List<PriceBar> second;
        try {
            second = fallback.getDailySeries(ticker, maxDays);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Both price providers failed for " + ticker + ": " + ex.getMessage(), ex);
        }

        return new ArrayList<>(second);
    }
}

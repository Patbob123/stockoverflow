package entities;

import java.util.*;

/**
 * Represents a user's portfolio containing a collection of stocks.
 */
public class Portfolio {
    private String name;
    // Map ticker -> Stock object
    private final Map<String, Stock> stocks;

    public Portfolio(String name) {
        this.name = name;
        this.stocks = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Stock> getStocks() {
        return stocks;
    }

    /**
     * Returns a list of all Stock objects in the portfolio.
     * Useful for iterating during analysis (User Story 9) or graphing (User Story 5).
     */
    public List<Stock> getStockList() {
        return new ArrayList<>(stocks.values());
    }

    public void addStock(Stock stock) {
        if (stock != null) {
            this.stocks.put(stock.getTicker(), stock);
        }
    }

    public void removeStock(String ticker) {
        this.stocks.remove(ticker);
    }

    public boolean hasStock(String ticker) {
        return this.stocks.containsKey(ticker);
    }

    /**
     * Calculates the total value of the portfolio based on the latest close prices.
     * Note: This assumes 1 unit of each stock if quantity is not tracked,
     * or can be modified later to support weighted portfolios.
     */
    public double getTotalValue() {
        double total = 0.0;
        for (Stock s : stocks.values()) {
            if (s.getClose() != null) {
                total += s.getClose();
            }
        }
        return total;
    }
}
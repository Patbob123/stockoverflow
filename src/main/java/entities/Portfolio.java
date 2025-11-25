package entities;

import java.util.*;

public class Portfolio {
    private String name;
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
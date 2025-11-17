package entities;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    private String name;
    private LocalDate creationDate;
    private LocalDate lastUpdated;
    private Map<String, Stock> stocks; // ticker to Stock
    private Map<String, Integer> stockQuantities; // Store the quantity of each stock

    public Portfolio(String name) {
        this.name = name;
        this.creationDate = LocalDate.now();
        this.lastUpdated = LocalDate.now();
        this.stocks = new HashMap<>();
        this.stockQuantities = new HashMap<>();
    }

    // Add stocks to the investment portfolio
    public void addStock(Stock stock, int quantity) {
        stocks.put(stock.getTicker(), stock);
        stockQuantities.put(stock.getTicker(), quantity);
        this.lastUpdated = LocalDate.now();
    }

    // Remove stocks from the investment portfolio
    public void removeStock(String ticker) {
        stocks.remove(ticker);
        stockQuantities.remove(ticker);
        this.lastUpdated = LocalDate.now();
    }

    // Update the number of stocks
    public void updateStockQuantity(String ticker, int quantity) {
        if (stocks.containsKey(ticker)) {
            stockQuantities.put(ticker, quantity);
            this.lastUpdated = LocalDate.now();
        }
    }

    // Calculate the total value of the investment portfolio
    public double getTotalValue() {
        double total = 0.0;
        for (Map.Entry<String, Stock> entry : stocks.entrySet()) {
            String ticker = entry.getKey();
            Stock stock = entry.getValue();
            Integer quantity = stockQuantities.get(ticker);
            if (stock.getClose() != null && quantity != null) {
                total += stock.getClose() * quantity;
            }
        }
        return total;
    }

    //  daily rate of return of the investment portfolio
    public double getDailyReturn() {
        double totalValue = getTotalValue();
        if (totalValue == 0) return 0;

        double totalReturn = 0.0;
        for (Map.Entry<String, Stock> entry : stocks.entrySet()) {
            String ticker = entry.getKey();
            Stock stock = entry.getValue();
            Integer quantity = stockQuantities.get(ticker);

            if (stock.getOpen() != null && stock.getClose() != null && quantity != null) {
                double stockValue = stock.getClose() * quantity;
                double weight = stockValue / totalValue;
                totalReturn += weight * stock.getDailyReturn();
            }
        }
        return totalReturn;
    }

    // Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public Map<String, Stock> getStocks() {
        return new HashMap<>(stocks); // Return to the copy
    }

    public Map<String, Integer> getStockQuantities() {
        return new HashMap<>(stockQuantities); // Return to the copy
    }

    public int getStockQuantity(String ticker) {
        return stockQuantities.getOrDefault(ticker, 0);
    }

    public void updateLastUpdated() {
        this.lastUpdated = LocalDate.now();
    }
}

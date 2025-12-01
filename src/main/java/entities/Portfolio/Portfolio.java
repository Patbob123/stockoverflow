package entities.Portfolio;

import entities.Simulation;
import entities.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.*;

@Getter
@Setter
public class Portfolio {
    public static final String[] PORTFOLIO_SORT = {"by name", "by close market price", "by amount of stock"};

    private String name;
    private ArrayList<String> visualStocks;
    private final Map<String, Stock> stocks = new HashMap<>();
    private final Map<String, Integer> stockAmount = new HashMap<>();
    private LocalTime time;
    @Getter
    @Setter
    private final Map<String, Comparator<String>> portfolioSortMap = new HashMap<>(Map.of(
            "by name", (ticker1, ticker2) -> stocks.get(ticker1).getTicker().compareTo(stocks.get(ticker2).getTicker()),
            "by close market price", (ticker1, ticker2) -> stocks.get(ticker1).getClose().compareTo(stocks.get(ticker2).getClose()),
            "by amount of stock", (ticker1, ticker2) -> stockAmount.get(ticker1).compareTo(stockAmount.get(ticker2))
    ));

    public Portfolio(String name, ArrayList<String> visualStocks, LocalTime time) {
        this.name = name;
        this.visualStocks = visualStocks;
        this.time = time;
    }

    public void addStock(Stock stock, Integer amount) {
        final String ticker = stock.getTicker();
        if (this.stocks.containsKey(ticker)) {
            this.stockAmount.put(ticker, stockAmount.get(ticker) + amount);
            return;
        }
        this.visualStocks.add(ticker);
        this.stocks.put(ticker, stock);
        this.stockAmount.put(ticker, amount);
    }

    public void removeStock(String ticker) {
        this.visualStocks.remove(ticker);
        this.stocks.remove(ticker);
    }

    public void removeStock(String ticker, Integer amount) {
        if (this.stockAmount.get(ticker) >= amount) {
            this.stockAmount.put(ticker, this.stockAmount.get(ticker) - amount);
        }
        else {
            this.visualStocks.remove(ticker);
        }
    }

    public Stock getStock(String stockName) {
        return this.stocks.get(stockName);
    }

    public void saveStockByJSON() {

    }

    public void sortStockBy(Comparator<String> method) {
        this.visualStocks.sort(method);
    }

    @Override
    public String toString() {
        return name;
    }
}

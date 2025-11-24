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
@AllArgsConstructor
public class Portfolio {
    private String name;
    private ArrayList<String> visualStocks;
    private final Map<String, Stock> stocks = new HashMap<>();
    private final Map<String, Integer> stockAmount = new HashMap<>();
    private LocalTime time;

    public Portfolio(String name){
        this.name = name; // TODO: REMOVE LATER THIS WAS JUST FOR TESTING
    }

    public void comparePortfolio(Portfolio portfolio, Simulation simulation) {

    }

    public void simulatePortfolio(Simulation simulation) {

    }

    public void sortPortfolio(){
        this.visualStocks.sort((a,b) -> stocks.get(a).getName().compareTo(stocks.get(b).getName()));
    }

    public void addStock(Stock stock, Integer amount){
        String ticker = stock.getTicker();
        if (this.stocks.containsKey(ticker)){
            this.stockAmount.put(ticker, stockAmount.get(ticker) + amount);
            return;
        }
        this.visualStocks.add(ticker);
        this.stocks.put(ticker, stock);
        this.stockAmount.put(ticker, amount);
    }

    public void removeStock(String ticker){
        this.visualStocks.remove(ticker);
        this.stocks.remove(ticker);
    }

    public void removeStock(String ticker, Integer amount){
        if (this.stockAmount.get(ticker)>=amount){
            this.stockAmount.put(ticker, this.stockAmount.get(ticker) - amount);
        }else
            this.visualStocks.remove(ticker);
    }

    public Stock getStock(String stockName){
        return this.stocks.get(stockName);
    }

    public void sortStockBy(Comparator<String> method) {
        this.visualStocks.sort(method);
    }

    public void generateGraph(){

    }

    public void exportToExcel(){

    }

    public void importFromExcel(){

    }

    @Override
    public String toString() {
        return name;
    }


}

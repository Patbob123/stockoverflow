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

    public void addStock(Stock stock){
        String ticker = stock.getTicker();
        this.visualStocks.add(ticker);
        this.stocks.put(ticker, stock);
    }

    public void removeStock(String ticker){
        this.visualStocks.remove(ticker);
        this.stocks.remove(ticker);
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

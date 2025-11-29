package entities.Portfolio;

import entities.Simulation;
import entities.Stock;

import java.time.LocalTime;
import java.util.*;

public class Portfolio {
    private String name;
    private ArrayList<String> visualStocks;
    private final Map<String, Stock> stocks = new HashMap<>();
    private LocalTime time;

    public Portfolio() {
    }

    public Portfolio(String name, ArrayList<String> visualStocks, LocalTime time) {
        this.name = name;
        this.visualStocks = visualStocks;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getVisualStocks() {
        return visualStocks;
    }

    public void setVisualStocks(ArrayList<String> visualStocks) {
        this.visualStocks = visualStocks;
    }

    public Map<String, Stock> getStocks() {
        return stocks;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
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
}

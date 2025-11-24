package use_case.portfolio;


import entities.Portfolio.Portfolio;
import entities.StatisticsCalculator;
import use_case.simulation.StockDataAccessInterface;

import java.time.LocalDate;
import java.util.*;

public class PortfolioMenuInteractor implements PortfolioMenuInputBoundary {

    private final PortfolioMenuOutputBoundary portfolioMenuOutputBoundary;
    private final Portfolio portfolio;
    private final StockDataAccessInterface stockDataAccessObject;

    public static final String STOCK_NOT_IN_PORTFOLIO = "Stock/stocks not found:";

    public PortfolioMenuInteractor(PortfolioMenuOutputBoundary output, Portfolio portfolio, StockDataAccessInterface stockDataAccessObject) {
        this.portfolioMenuOutputBoundary = output;
        this.portfolio = portfolio;
        this.stockDataAccessObject = stockDataAccessObject;
    }

    @Override
    public void executeAddStock() {
        this.portfolioMenuOutputBoundary.prepareAddStockView(this.portfolio);
    }

    @Override
    public void executeRemoveStock(ArrayList<String> stocks) {
        if (this.portfolio == null) return;

        for (String stock :  stocks) {
            try {this.portfolio.removeStock(stock);
                stocks.remove(stock);
            }catch(NullPointerException npe) {
                this.portfolioMenuOutputBoundary.prepareRemoveStockView(this.portfolio);
                this.portfolioMenuOutputBoundary.prepareFailView(this.STOCK_NOT_IN_PORTFOLIO + stocks);
                return;
            }
        }
    }

    @Override
    public void executeSimulation() {
        //TODO:
        this.portfolioMenuOutputBoundary.prepareSimulationView(this.portfolio);
    }

    @Override
    public void executeCompare(Portfolio comparePortfolio) {
       //TODO
        this.portfolioMenuOutputBoundary.prepareCompareView(this.portfolio, comparePortfolio);
    }

    @Override
    public void executeSelectAll() {

    }

    @Override
    public void executeClearSelection() {

    }

    @Override
    public void executeSavePortfolio() {
        this.portfolioMenuOutputBoundary.prepareSaveView(this.portfolio);
    }

    @Override
    public void executeExit() {
        this.portfolioMenuOutputBoundary.prepareExitView();
    }

    @Override
    public void executeLoadMarketData() {
        if (stockDataAccessObject == null) {
            this.portfolioMenuOutputBoundary.prepareFailView("Data Access Object not initialized.");
            return;
        }
        List<String> tickers = stockDataAccessObject.getAllAvailableTickers();
        this.portfolioMenuOutputBoundary.prepareMarketData(tickers);
    }

    @Override
    public void executeAnalyze(List<String> tickers) {
        if (stockDataAccessObject == null) return;

        if (tickers == null || tickers.isEmpty()) {
            this.portfolioMenuOutputBoundary.prepareFailView("No stocks selected for analysis.");
            return;
        }

        try {
            Map<String, Double> results = new HashMap<>();

            // We will plot the normalized price history for comparison
            // To simplify plotting, we'll find the common date range or just take the shortest one.
            // For this implementation, let's assume all stocks have aligned data from Mock DAO.

            List<double[]> allPaths = new ArrayList<>();
            int minLength = Integer.MAX_VALUE;

            for (String ticker : tickers) {
                Map<LocalDate, Double> prices = stockDataAccessObject.getHistoricalPrices(ticker);
                if (prices.isEmpty()) continue;

                Collection<Double> values = prices.values();
                double[] priceArray = values.stream().mapToDouble(Double::doubleValue).toArray();

                if (priceArray.length < minLength) minLength = priceArray.length;

                // Calculate basic stats
                StatisticsCalculator stats = new StatisticsCalculator();
                // returns
                double[] returns = new double[priceArray.length - 1];
                for (int i = 0; i < priceArray.length - 1; i++) {
                    returns[i] = (priceArray[i+1] - priceArray[i]) / priceArray[i];
                }

                double vol = stats.calculateVolatility(returns) * Math.sqrt(252); // Annualized
                results.put(ticker + " Volatility", vol);

                double mean = stats.mean(returns) * 252; // Annualized
                results.put(ticker + " Mean Return", mean);

                allPaths.add(priceArray);
            }

            // Truncate paths to minLength for plotting together
            double[][] plotData = new double[allPaths.size()][minLength];
            for (int i = 0; i < allPaths.size(); i++) {
                double[] src = allPaths.get(i);
                // Copy last minLength elements to align by latest date
                System.arraycopy(src, src.length - minLength, plotData[i], 0, minLength);
            }

            this.portfolioMenuOutputBoundary.prepareAnalysisResult("Analysis Complete", results, plotData);

        } catch (Exception e) {
            this.portfolioMenuOutputBoundary.prepareFailView("Analysis Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

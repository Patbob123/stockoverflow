package use_case.portfolio;


import entities.Portfolio.Portfolio;
import entities.Stock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PortfolioMenuInteractor implements PortfolioMenuInputBoundary {

    private final PortfolioMenuOutputBoundary portfolioMenuOutputBoundary;

    private final Portfolio portfolio;

    public static final String STOCK_NOT_IN_PORTFOLIO = "Stock/stocks not found:";

    public PortfolioMenuInteractor(PortfolioMenuOutputBoundary output, Portfolio portfolio) {
        this.portfolioMenuOutputBoundary = output;
        this.portfolio = portfolio;
    }

    // --- Helper: Mock Data Generator (Since no API is connected yet) ---
    private void ensureHistoricalData(Stock stock) {
        if (stock.getHistoricalPrices() == null || stock.getHistoricalPrices().isEmpty()) {
            Map<LocalDate, Double> history = new TreeMap<>();
            LocalDate today = LocalDate.now();
            // Start with a base price based on the ticker hash to be consistent but different
            double price = 100.0 + (stock.getTicker().hashCode() % 50);

            // Generate 30 days of dummy data
            for (int i = 30; i >= 0; i--) {
                // Random fluctuation between -2% and +2%
                price = price * (1 + (Math.random() * 0.04 - 0.02));
                history.put(today.minusDays(i), price);
            }
            stock.setHistoricalPrices(history);
            // Update current price for consistency
            stock.updateQuote(today, price, price, price + 1, price - 1);
        }
    }

    @Override
    public void executeAddStock() {
        this.portfolioMenuOutputBoundary.prepareAddStockView(this.portfolio);
    }

    @Override
    public void executeRemoveStock(ArrayList<String> stocks) {
        for (String stockTicker : stocks) {
            try {
                this.portfolio.removeStock(stockTicker);
            } catch (Exception e) {
                this.portfolioMenuOutputBoundary.prepareFailView(STOCK_NOT_IN_PORTFOLIO + stockTicker);
            }
        }
        // After removal, simply refresh the view (State update is handled by View's refresh logic in this architecture)
        this.portfolioMenuOutputBoundary.prepareRemoveStockView(this.portfolio);
    }

    // Implementation of User Story 5: Graph multiple stocks
    @Override
    public void executeGraph(List<String> selectedTickers) {
        if (selectedTickers == null || selectedTickers.isEmpty()) {
            portfolioMenuOutputBoundary.prepareFailView("Please select at least one stock to graph.");
            return;
        }

        List<Stock> stocksToGraph = new ArrayList<>();

        for (String ticker : selectedTickers) {
            Stock stock = portfolio.getStock(ticker);
            if (stock != null) {
                // IMPORTANT: Ensure data exists (using mock data if API is missing)
                ensureHistoricalData(stock);
                stocksToGraph.add(stock);
            }
        }

        if (stocksToGraph.isEmpty()) {
            portfolioMenuOutputBoundary.prepareFailView("No valid stocks found for graphing.");
        } else {
            portfolioMenuOutputBoundary.prepareGraphView(stocksToGraph);
        }
    }

    // Implementation of User Story 9: Historical portfolio return analysis
    @Override
    public void executeHistoricalAnalysis(int daysAgo) {
        double totalInitialValue = 0.0;
        double totalFinalValue = 0.0;
        boolean hasData = false;

        // Calculate portfolio return assuming equal weight (1 share each) for simplicity
        for (Stock stock : portfolio.getStocks().values()) {
            ensureHistoricalData(stock); // Ensure data exists

            Map<LocalDate, Double> history = stock.getHistoricalPrices();
            if (history.isEmpty()) continue;

            // Get the most recent price (Close price)
            Double endPrice = stock.getClose();
            if (endPrice == null) {
                // Fallback to last entry in history
                endPrice = new ArrayList<>(history.values()).get(history.size() - 1);
            }

            // Find price 'daysAgo'
            LocalDate targetDate = LocalDate.now().minusDays(daysAgo);
            Double startPrice = endPrice; // Default to no change if data missing

            // Find the first available date after or on targetDate
            for (Map.Entry<LocalDate, Double> entry : history.entrySet()) {
                if (!entry.getKey().isBefore(targetDate)) {
                    startPrice = entry.getValue();
                    break;
                }
            }

            totalInitialValue += startPrice;
            totalFinalValue += endPrice;
            hasData = true;
        }

        if (!hasData || totalInitialValue == 0) {
            portfolioMenuOutputBoundary.prepareFailView("Insufficient historical data to analyze returns.");
            return;
        }

        // Calculate Return ROI = (Final - Initial) / Initial
        double returnRate = (totalFinalValue - totalInitialValue) / totalInitialValue;

        // Send percentage to presenter
        portfolioMenuOutputBoundary.prepareAnalysisView(returnRate * 100);
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
}

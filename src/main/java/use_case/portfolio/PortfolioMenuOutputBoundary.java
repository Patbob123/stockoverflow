package use_case.portfolio;

import entities.Portfolio.Portfolio;
import java.util.List;
import java.util.Map;

public interface PortfolioMenuOutputBoundary {
    void prepareAddStockView(Portfolio portfolio);

    void prepareRemoveStockView(Portfolio portfolio);

    void prepareSimulationView(Portfolio portfolio);

    void prepareCompareView(Portfolio portfolio, Portfolio comparePortfolio);

    void prepareSaveView(Portfolio portfolio);

    void prepareFailView(String message);

    void prepareExitView();

    // New methods for Market Analysis
    void prepareMarketData(List<String> tickers);
    void prepareAnalysisResult(String resultMessage, Map<String, Double> stats, double[][] plotData);
}

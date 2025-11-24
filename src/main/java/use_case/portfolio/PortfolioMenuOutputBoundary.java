package use_case.portfolio;

import entities.Portfolio.Portfolio;
import entities.Stock;

import java.util.List;

public interface PortfolioMenuOutputBoundary {
    void prepareAddStockView(Portfolio portfolio);

    void prepareRemoveStockView(Portfolio portfolio);

    void prepareGraphView(List<Stock> stocks);

    void prepareAnalysisView(double returnPercentage);

    void prepareCompareView(Portfolio portfolio, Portfolio comparePortfolio);

    void prepareSaveView(Portfolio portfolio);

    void prepareFailView(String message);

    void prepareExitView();
}

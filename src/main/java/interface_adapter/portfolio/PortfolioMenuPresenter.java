package interface_adapter.portfolio;

import entities.Portfolio.Portfolio;
import entities.Stock;
import use_case.portfolio.PortfolioMenuOutputBoundary;
import java.util.List;

public class PortfolioMenuPresenter implements PortfolioMenuOutputBoundary {
    private final PortfolioMenuViewModel portfolioMenuViewModel;

    public PortfolioMenuPresenter(PortfolioMenuViewModel portfolioMenuViewModel) {
        this.portfolioMenuViewModel = portfolioMenuViewModel;
    }

    @Override
    public void prepareAddStockView(Portfolio portfolio) {

    }

    @Override
    public void prepareRemoveStockView(Portfolio portfolio) {
        portfolioMenuViewModel.firePropertyChange("state");
    }

    @Override
    public void prepareGraphView(List<Stock> stocks) {
        PortfolioMenuState state = portfolioMenuViewModel.getState();
        state.setStocksToGraph(stocks);
        portfolioMenuViewModel.firePropertyChange("graph");
    }

    @Override
    public void prepareAnalysisView(double returnPercentage) {
        PortfolioMenuState state = portfolioMenuViewModel.getState();
        String sign = returnPercentage >= 0 ? "+" : "";
        String msg = String.format("Historical Return (Last 30 Days): %s%.2f%%", sign, returnPercentage);
        state.setAnalysisResult(msg);
        // Fire event to tell View to show the result dialog
        portfolioMenuViewModel.firePropertyChange("analysis");
    }

    @Override
    public void prepareCompareView(Portfolio portfolio, Portfolio comparePortfolio) {
    }

    @Override
    public void prepareSaveView(Portfolio portfolio) {
        // Logic to save
    }

    @Override
    public void prepareFailView(String message) {
        PortfolioMenuState state = portfolioMenuViewModel.getState();
        state.setError(message);
        portfolioMenuViewModel.firePropertyChange("error");
    }

    @Override
    public void prepareExitView() {

    }
}
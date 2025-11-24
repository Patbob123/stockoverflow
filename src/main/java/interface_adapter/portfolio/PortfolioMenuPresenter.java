package interface_adapter.portfolio;

import entities.Portfolio.Portfolio;
import use_case.portfolio.PortfolioMenuOutputBoundary;
import java.util.List;
import java.util.Map;

public class PortfolioMenuPresenter implements PortfolioMenuOutputBoundary {
    private final PortfolioMenuViewModel portfolioMenuViewModel;

    public PortfolioMenuPresenter(PortfolioMenuViewModel portfolioMenuViewModel) {
        this.portfolioMenuViewModel = portfolioMenuViewModel;
    }

    @Override
    public void prepareAddStockView(Portfolio portfolio) {
        // Implementation kept as is
    }

    @Override
    public void prepareRemoveStockView(Portfolio portfolio) {
         // Implementation kept as is
    }

    @Override
    public void prepareSimulationView(Portfolio portfolio) {
         // Implementation kept as is
    }

    @Override
    public void prepareCompareView(Portfolio portfolio, Portfolio comparePortfolio) {
         // Implementation kept as is
    }

    @Override
    public void prepareSaveView(Portfolio portfolio) {
         // Implementation kept as is
    }

    @Override
    public void prepareFailView(String message) {
        System.out.println("Error: " + message);
        // Could update state to show error
    }

    @Override
    public void prepareExitView() {
         // Implementation kept as is
    }

    @Override
    public void prepareMarketData(List<String> tickers) {
        PortfolioMenuState state = portfolioMenuViewModel.getState();
        state.setAvailableStocks(tickers);
        portfolioMenuViewModel.setState(state);
        portfolioMenuViewModel.firePropertyChange();
    }

    @Override
    public void prepareAnalysisResult(String resultMessage, Map<String, Double> stats, double[][] plotData) {
        PortfolioMenuState state = portfolioMenuViewModel.getState();
        state.setAnalysisMessage(resultMessage);
        state.setAnalysisStats(stats);
        state.setAnalysisPlotData(plotData);
        portfolioMenuViewModel.setState(state);
        portfolioMenuViewModel.firePropertyChange();
    }
}

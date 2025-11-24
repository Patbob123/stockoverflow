package interface_adapter.portfolio;

import entities.Portfolio.Portfolio;
import use_case.portfolio.PortfolioMenuOutputBoundary;

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

    }

    @Override
    public void prepareSimulationView(Portfolio portfolio) {

    }

    @Override
    public void prepareCompareView(Portfolio portfolio, Portfolio comparePortfolio) {

    }

    @Override
    public void prepareSaveView(Portfolio portfolio) {

    }

    @Override
    public void prepareFailView(String message) {

    }

    @Override
    public void prepareExitView() {

    }
}

package use_case.portfolio;

import entities.Portfolio.Portfolio;
import interface_adapter.ViewModel;
import interface_adapter.portfolio.addStock.AddStockMenuState;
import interface_adapter.portfolio.addStock.AddStockMenuViewModel;
import use_case.InputBoundary;
import use_case.OutputBoundary;

public interface PortfolioMenuOutputBoundary extends OutputBoundary {
    void prepareAddStockView(ViewModel<AddStockMenuState> addStockMenuViewModel);

    void prepareRemoveStockView(Portfolio portfolio);

    void prepareSimulationView(Portfolio portfolio);

    void prepareCompareView(Portfolio portfolio, Portfolio comparePortfolio);

    void prepareSaveView(Portfolio portfolio);

    void prepareFailView(String message);

    void prepareExitView();
}

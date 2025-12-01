package use_case.portfolio;

import entities.Portfolio.Portfolio;
import interface_adapter.ViewModel;
import interface_adapter.portfolio.addStock.AddStockMenuState;
import interface_adapter.portfolio.addStock.AddStockMenuViewModel;
import use_case.InputBoundary;

import java.util.ArrayList;

public interface PortfolioMenuInputBoundary extends InputBoundary {

    void executeUpdatePortfolio(Portfolio portfolio);

    void executeAddStock(ViewModel<AddStockMenuState> addStockMenuViewModel);

    void executeRemoveStock(ArrayList<String> stocks);

    void executeSimulation();

    void executeCompare(Portfolio comparePortfolio);

    void executeSelectAll();

    void executeClearSelection();

    void executeSavePortfolio();

    void executeExit();

}

package use_case.portfolio;

import entities.Portfolio.Portfolio;

import java.util.ArrayList;

public interface PortfolioMenuInputBoundary {

    void executeAddStock();

    void executeRemoveStock(ArrayList<String> stocks);

    void executeSimulation();

    void executeCompare(Portfolio comparePortfolio);

    void executeSelectAll();

    void executeClearSelection();

    void executeSavePortfolio();

    void executeExit();

}

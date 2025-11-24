package use_case.portfolio;


import entities.Portfolio.Portfolio;

import java.util.ArrayList;

public class PortfolioMenuInteractor implements PortfolioMenuInputBoundary {

    private final PortfolioMenuOutputBoundary portfolioMenuOutputBoundary;

    private final Portfolio portfolio;

    public static final String STOCK_NOT_IN_PORTFOLIO = "Stock/stocks not found:";

    public PortfolioMenuInteractor(PortfolioMenuOutputBoundary output, Portfolio portfolio) {
        this.portfolioMenuOutputBoundary = output;
        this.portfolio = portfolio;
    }

    @Override
    public void executeAddStock() {
        this.portfolioMenuOutputBoundary.prepareAddStockView(this.portfolio);
    }

    @Override
    public void executeRemoveStock(ArrayList<String> stocks) {
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
}

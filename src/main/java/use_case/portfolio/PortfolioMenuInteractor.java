package use_case.portfolio;


import entities.Portfolio.Portfolio;
import interface_adapter.ViewModel;
import interface_adapter.portfolio.addStock.AddStockMenuState;
import interface_adapter.portfolio.addStock.AddStockMenuViewModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;

public class PortfolioMenuInteractor implements PortfolioMenuInputBoundary {

    private final PortfolioMenuOutputBoundary portfolioMenuOutputBoundary;

    @Getter
    @Setter
    private Portfolio portfolio;

    public static final String STOCK_NOT_IN_PORTFOLIO = "Stock/stocks not found:";

    public PortfolioMenuInteractor(PortfolioMenuOutputBoundary output) {

        this.portfolioMenuOutputBoundary = output;
    }

    @Override
    public void executeUpdatePortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    @Override
    public void executeAddStock(ViewModel<AddStockMenuState> addStockMenuViewModel) {
        this.portfolioMenuOutputBoundary.prepareAddStockView(addStockMenuViewModel);
    }

    @Override
    public void executeRemoveStock(ArrayList<String> stocks) {
        for (String stock : stocks) {
            try {
                this.portfolio.removeStock(stock);
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
        this.portfolioMenuOutputBoundary.prepareSimulationView(this.portfolio);
    }

    @Override
    public void executeCompare(Portfolio comparePortfolio) {
        this.portfolioMenuOutputBoundary.prepareCompareView(this.portfolio, comparePortfolio);
    }

    @Override
    public void executeSavePortfolio() {
        this.portfolioMenuOutputBoundary.prepareSaveView(this.portfolio);
    }

    @Override
    public void executeExit() {
        this.portfolioMenuOutputBoundary.prepareExitView();
    }

    @Override
    public Portfolio getPortfolio() {
        return this.portfolio;
    }

    @Override
    public void sortPortfolio(Portfolio portfolio, String method) {
        portfolio.sortStockBy(portfolio.getPortfolioSortMap().get(method));
    }

}

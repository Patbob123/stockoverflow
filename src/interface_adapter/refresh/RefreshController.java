package interface_adapter.refresh;

import entities.Portfolio;
import entities.Stock;
import use_case.refresh.RefreshInputBoundary;

public class RefreshController {
    private final RefreshInputBoundary refreshUseCaseInteractor;

    public RefreshController(RefreshInputBoundary refreshUseCaseInteractor) {
        this.refreshUseCaseInteractor = refreshUseCaseInteractor;
    }

    public void refreshStock(String ticker, Stock currentStock) {
        refreshUseCaseInteractor.refreshStock(ticker, currentStock);
    }

    public void refreshPortfolio(Portfolio portfolio) {
        refreshUseCaseInteractor.refreshPortfolio(portfolio);
    }
}
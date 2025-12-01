package use_case.portfolio.addStock;

import entities.Portfolio.Portfolio;

public class AddStockMenuInteractor implements AddstockMenuInputBoundary {

    private AddStockMenuOutputBoundary addStockMenuPresenter;

    public AddStockMenuInteractor(AddStockMenuOutputBoundary output) {
        this.addStockMenuPresenter = output;
    }

    @Override
    public void executeAddstock(String stockTicker) {
        //Todo: Use API
    }

    @Override
    public void executeExit() {
        addStockMenuPresenter.prepareGoBackView();
    }
}

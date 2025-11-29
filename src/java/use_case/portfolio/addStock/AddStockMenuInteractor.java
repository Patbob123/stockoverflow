package use_case.portfolio.addStock;

public class AddStockMenuInteractor implements AddstockMenuInputBoundary{

    private Portfolio portfolio;
    private AddStockMenuOutputBoundary addStockMenuPresenter;

    public AddStockMenuInteractor(Portfolio portfolio, AddStockMenuOutputBoundary output) {
        this.portfolio = portfolio;
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

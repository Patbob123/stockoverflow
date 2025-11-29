package interface_adapter.portfolio.addStock;

import use_case.portfolio.addStock.AddStockMenuOutputBoundary;

public class AddStockMenuPresenter implements AddStockMenuOutputBoundary {
    private final AddStockMenuViewModel addStockMenuViewModel;

    public AddStockMenuPresenter(AddStockMenuViewModel mainMenuViewModel) {
        this.addStockMenuViewModel = mainMenuViewModel;
    }

    @Override
    public void prepareSuccessView(String portfolioName, String stockticker) {
        System.out.println(stockticker+ "Shoudld be added");
        addStockMenuViewModel.firePropertyChange();
    }

    @Override
    public void prepareGoBackView() {
        addStockMenuViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailureView(String message) {
        addStockMenuViewModel.firePropertyChange();
    }
}

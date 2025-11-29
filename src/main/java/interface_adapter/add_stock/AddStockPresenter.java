package interface_adapter.add_stock;

import use_case.add_stock.AddStockOutputBoundary;
import use_case.add_stock.AddStockOutputData;

public class AddStockPresenter implements AddStockOutputBoundary {
    private final AddStockViewModel viewModel;

    public AddStockPresenter(AddStockViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(AddStockOutputData outputData) {
        AddStockState state = viewModel.getState();
        state.setMessage(outputData.getMessage());
        // Clear search input but keep portfolio name
        state.setSearchInput("");
        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        AddStockState state = viewModel.getState();
        state.setMessage("Error: " + error);
        viewModel.firePropertyChanged();
    }
}

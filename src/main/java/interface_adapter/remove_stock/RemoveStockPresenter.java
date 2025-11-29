package interface_adapter.remove_stock;

import interface_adapter.add_stock.AddStockState;
import interface_adapter.add_stock.AddStockViewModel;
import use_case.remove_stock.RemoveStockOutputBoundary;
import use_case.remove_stock.RemoveStockOutputData;

public class RemoveStockPresenter implements RemoveStockOutputBoundary {
    private final AddStockViewModel viewModel;

    public RemoveStockPresenter(AddStockViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(RemoveStockOutputData outputData) {
        AddStockState state = viewModel.getState();
        state.setMessage(outputData.getMessage());
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

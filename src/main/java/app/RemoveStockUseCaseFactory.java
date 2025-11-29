package app;

import interface_adapter.add_stock.AddStockViewModel;
import interface_adapter.remove_stock.RemoveStockController;
import interface_adapter.remove_stock.RemoveStockPresenter;
import use_case.UserDataAccessInterface;
import use_case.remove_stock.RemoveStockInputBoundary;
import use_case.remove_stock.RemoveStockInteractor;
import use_case.remove_stock.RemoveStockOutputBoundary;

public class RemoveStockUseCaseFactory {
    private RemoveStockUseCaseFactory() {}

    public static RemoveStockController createRemoveStockUseCase(
            AddStockViewModel viewModel,
            UserDataAccessInterface userDataAccess) {

        RemoveStockOutputBoundary outputBoundary = new RemoveStockPresenter(viewModel);
        RemoveStockInputBoundary interactor = new RemoveStockInteractor(userDataAccess, outputBoundary);

        return new RemoveStockController(interactor);
    }
}

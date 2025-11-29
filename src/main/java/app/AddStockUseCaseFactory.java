package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.add_stock.AddStockController;
import interface_adapter.add_stock.AddStockPresenter;
import interface_adapter.add_stock.AddStockViewModel;
import interface_adapter.portfolio_analysis.PortfolioAnalysisController;
import interface_adapter.remove_stock.RemoveStockController;
import interface_adapter.show_graph.ShowGraphController;
import use_case.APIDataAccessInterface;
import use_case.UserDataAccessInterface;
import use_case.add_stock.AddStockInputBoundary;
import use_case.add_stock.AddStockInteractor;
import use_case.add_stock.AddStockOutputBoundary;
import view.AddStockView;

public class AddStockUseCaseFactory {
    private AddStockUseCaseFactory() {}

    public static AddStockView create(
            ViewManagerModel viewManagerModel,
            AddStockViewModel addStockViewModel,
            ShowGraphController showGraphController,
            PortfolioAnalysisController analysisController,
            UserDataAccessInterface userDataAccess,
            APIDataAccessInterface apiDataAccess) {

        AddStockController addController = createAddUseCase(addStockViewModel, userDataAccess, apiDataAccess);
        RemoveStockController removeController = RemoveStockUseCaseFactory.createRemoveStockUseCase(addStockViewModel, userDataAccess);

        return new AddStockView(addStockViewModel, addController, removeController, showGraphController, analysisController, viewManagerModel, userDataAccess, apiDataAccess);
    }

    private static AddStockController createAddUseCase(
            AddStockViewModel viewModel,
            UserDataAccessInterface userDataAccess,
            APIDataAccessInterface apiDataAccess) {

        AddStockOutputBoundary outputBoundary = new AddStockPresenter(viewModel);
        AddStockInputBoundary interactor = new AddStockInteractor(userDataAccess, apiDataAccess, outputBoundary);
        return new AddStockController(interactor);
    }
}
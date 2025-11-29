package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_portfolio.CreatePortfolioController;
import interface_adapter.create_portfolio.CreatePortfolioPresenter;
import interface_adapter.create_portfolio.CreatePortfolioViewModel;
import interface_adapter.mainmenu.MainMenuController;
import use_case.UserDataAccessInterface;
import use_case.create_portfolio.CreatePortfolioInputBoundary;
import use_case.create_portfolio.CreatePortfolioInteractor;
import use_case.create_portfolio.CreatePortfolioOutputBoundary;
import view.PortfolioMenuView;

public class CreatePortfolioUseCaseFactory {
    private CreatePortfolioUseCaseFactory() {}

    public static PortfolioMenuView create(
            ViewManagerModel viewManagerModel,
            CreatePortfolioViewModel viewModel,
            UserDataAccessInterface userDataAccess,
            MainMenuController mainMenuController) {

        CreatePortfolioController controller = createUseCase(viewManagerModel, viewModel, userDataAccess);

        return new PortfolioMenuView(viewModel, controller, userDataAccess, viewManagerModel, mainMenuController);
    }

    private static CreatePortfolioController createUseCase(
            ViewManagerModel viewManagerModel,
            CreatePortfolioViewModel viewModel,
            UserDataAccessInterface userDataAccess) {

        CreatePortfolioOutputBoundary outputBoundary = new CreatePortfolioPresenter(viewModel, viewManagerModel);
        CreatePortfolioInputBoundary interactor = new CreatePortfolioInteractor(userDataAccess, outputBoundary);
        return new CreatePortfolioController(interactor);
    }
}
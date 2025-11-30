package app;

import interface_adapter.Login.LoginViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.create_portfolio.CreatePortfolioViewModel;
import interface_adapter.mainmenu.MainMenuController;
import interface_adapter.mainmenu.MainMenuPresenter;
import interface_adapter.mainmenu.MainMenuViewModel;
import interface_adapter.search.SearchViewModel;
// Import AddStockViewModel
import interface_adapter.add_stock.AddStockViewModel;

import use_case.mainmenu.MainMenuInputBoundary;
import use_case.mainmenu.MainMenuInteractor;
import use_case.mainmenu.MainMenuOutputBoundary;
import view.MainMenuView;

import javax.swing.*;
import java.io.IOException;

public class MainMenuUseCaseFactory {

    private MainMenuUseCaseFactory() {}

    public static MainMenuView create(
            ViewManagerModel viewManagerModel,
            MainMenuViewModel mainMenuViewModel,
            CreatePortfolioViewModel createPortfolioViewModel,
            SearchViewModel searchViewModel,
            LoginViewModel loginViewModel,
            AddStockViewModel addStockViewModel) { // Added Argument

        try {
            MainMenuController mainMenuController = createMainMenuUseCase(
                    viewManagerModel,
                    mainMenuViewModel,
                    createPortfolioViewModel,
                    searchViewModel,
                    loginViewModel,
                    addStockViewModel); // Pass it down

            return new MainMenuView(mainMenuViewModel, mainMenuController);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open user data file.");
        }

        return null;
    }

    private static MainMenuController createMainMenuUseCase(
            ViewManagerModel viewManagerModel,
            MainMenuViewModel mainMenuViewModel,
            CreatePortfolioViewModel createPortfolioViewModel,
            SearchViewModel searchViewModel,
            LoginViewModel loginViewModel,
            AddStockViewModel addStockViewModel) throws IOException { // Added Argument

        // Updated Constructor call to match your MainMenuPresenter changes
        MainMenuOutputBoundary mainMenuOutputBoundary = new MainMenuPresenter(
                viewManagerModel,
                mainMenuViewModel,
                loginViewModel,
                createPortfolioViewModel,
                searchViewModel,
                addStockViewModel // Injecting dependency
        );

        MainMenuInputBoundary mainMenuInteractor = new MainMenuInteractor(mainMenuOutputBoundary);

        return new MainMenuController(mainMenuInteractor);
    }
}
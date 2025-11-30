package interface_adapter.mainmenu;

import app.*;

import data_access.FileUserDataAccessObject;
import data_access.StockDataAccessObject;

import entities.CommonUserFactory;

import interface_adapter.ViewManagerModel;
import interface_adapter.Login.LoginViewModel;
import interface_adapter.Signup.SignupViewModel;
import interface_adapter.search.SearchViewModel;
import interface_adapter.show_graph.ShowGraphController;
import interface_adapter.show_graph.ShowGraphViewModel;
import interface_adapter.create_portfolio.CreatePortfolioViewModel;
import interface_adapter.add_stock.AddStockViewModel;
import interface_adapter.portfolio_analysis.PortfolioAnalysisController;
import interface_adapter.portfolio_analysis.PortfolioAnalysisViewModel;
import interface_adapter.monte_carlo.MonteCarloController;
import interface_adapter.monte_carlo.MonteCarloViewModel;
import app.MonteCarloUseCaseFactory;

import view.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Ensure Swing components are created on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {

            // 1. Main Window Setup
            JFrame application = new JFrame("Stock Overflow");
            application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            application.setSize(1000, 800);

            CardLayout cardLayout = new CardLayout();
            JPanel views = new JPanel(cardLayout);
            application.add(views);

            // 2. View Manager
            ViewManagerModel viewManagerModel = new ViewManagerModel();
            new ViewManager(views, cardLayout, viewManagerModel);

            // 3. ViewModels
            LoginViewModel loginViewModel = new LoginViewModel();
            SignupViewModel signupViewModel = new SignupViewModel();
            MainMenuViewModel mainMenuViewModel = new MainMenuViewModel();
            SearchViewModel searchViewModel = new SearchViewModel();
            ShowGraphViewModel showGraphViewModel = new ShowGraphViewModel();
            CreatePortfolioViewModel createPortfolioViewModel = new CreatePortfolioViewModel();
            AddStockViewModel addStockViewModel = new AddStockViewModel();
            PortfolioAnalysisViewModel portfolioAnalysisViewModel = new PortfolioAnalysisViewModel();
            MonteCarloViewModel monteCarloViewModel = new MonteCarloViewModel();

            // 4. Data Access Objects
            FileUserDataAccessObject userDataAccessObject;
            try {
                userDataAccessObject = new FileUserDataAccessObject("./users.csv", new CommonUserFactory());
            } catch (IOException e) {
                throw new RuntimeException("Could not create/open user data file.", e);
            }

            StockDataAccessObject stockDataAccessObject = new StockDataAccessObject();

            // 5. Independent Controllers
            ShowGraphController showGraphController = ShowGraphUseCaseFactory.createShowGraphUseCase(
                    viewManagerModel,
                    showGraphViewModel,
                    stockDataAccessObject
            );

            PortfolioAnalysisController portfolioAnalysisController = PortfolioAnalysisUseCaseFactory.createPortfolioAnalysisUseCase(
                    viewManagerModel,
                    portfolioAnalysisViewModel,
                    userDataAccessObject,
                    stockDataAccessObject
            );

            MonteCarloController monteCarloController = MonteCarloUseCaseFactory.create(
                    monteCarloViewModel,
                    stockDataAccessObject
            );

            // 6. Assemble Views

            // --- Signup View ---
            SignupView signupView = SignupUseCaseFactory.create(
                    viewManagerModel,
                    loginViewModel,
                    signupViewModel,
                    userDataAccessObject
            );
            views.add(signupView, signupView.viewName);

            // --- Login View ---
            LoginView loginView = LoginUseCaseFactory.create(
                    viewManagerModel,
                    loginViewModel,
                    mainMenuViewModel,
                    userDataAccessObject
            );
            views.add(loginView, loginView.viewName);

            // --- Main Menu View ---
            // Main is now in the same package as MainMenuController, but Factory creates it
            MainMenuView mainMenuView = MainMenuUseCaseFactory.create(
                    viewManagerModel,
                    mainMenuViewModel,
                    createPortfolioViewModel,
                    searchViewModel,
                    loginViewModel,
                    addStockViewModel
            );
            views.add(mainMenuView, mainMenuView.viewName);

            MainMenuController mainMenuController = mainMenuView.getMainMenuController();

            // --- Portfolio Menu View (Create Portfolio) ---
            PortfolioMenuView portfolioMenuView = CreatePortfolioUseCaseFactory.create(
                    viewManagerModel,
                    createPortfolioViewModel,
                    userDataAccessObject,
                    mainMenuController
            );
            views.add(portfolioMenuView, portfolioMenuView.viewName);

            // --- Add Stock View ---
            AddStockView addStockView = AddStockUseCaseFactory.create(
                    viewManagerModel,
                    addStockViewModel,
                    showGraphController,
                    portfolioAnalysisController,
                    monteCarloController,
                    monteCarloViewModel,
                    userDataAccessObject,
                    stockDataAccessObject
            );
            views.add(addStockView, addStockView.viewName);

            // --- Search View ---
            SearchView searchView = SearchUseCaseFactory.create(
                    viewManagerModel,
                    searchViewModel,
                    showGraphController,
                    stockDataAccessObject
            );
            views.add(searchView, searchView.viewName);

            // --- Show Graph View ---
            ShowGraphView showGraphView = new ShowGraphView(
                    showGraphViewModel,
                    showGraphController,
                    viewManagerModel
            );
            views.add(showGraphView, showGraphView.viewName);

            // --- Portfolio Analysis View ---
            // Updated to match the 3-argument constructor we fixed earlier
            PortfolioAnalysisView portfolioAnalysisView = new PortfolioAnalysisView(
                    portfolioAnalysisViewModel,
                    portfolioAnalysisController,
                    viewManagerModel
            );
            views.add(portfolioAnalysisView, portfolioAnalysisView.viewName);

            // 7. Start Application
            viewManagerModel.setActiveView(loginView.viewName);
            viewManagerModel.firePropertyChanged();

            application.pack();
            application.setLocationRelativeTo(null);
            application.setVisible(true);
        });
    }
}
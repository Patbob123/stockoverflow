package interface_adapter.mainmenu;

import app.*;
import data_access.FileUserDataAccessObject;
import data_access.StockDataAccessObject;

import entities.CommonUserFactory;

import interface_adapter.ViewManagerModel;

import interface_adapter.Login.LoginViewModel;
import interface_adapter.Signup.SignupViewModel;
import interface_adapter.mainmenu.MainMenuController;
import interface_adapter.mainmenu.MainMenuViewModel;
import interface_adapter.singlestock.SingleStockViewModel;
import interface_adapter.create_portfolio.CreatePortfolioViewModel;
import interface_adapter.add_stock.AddStockViewModel;
import interface_adapter.search.SearchViewModel;
import interface_adapter.show_graph.ShowGraphController;
import interface_adapter.show_graph.ShowGraphViewModel;
import interface_adapter.portfolio_analysis.PortfolioAnalysisController;
import interface_adapter.portfolio_analysis.PortfolioAnalysisViewModel;
import interface_adapter.monte_carlo.MonteCarloController;
import interface_adapter.monte_carlo.MonteCarloViewModel;

import view.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame application = new JFrame("Stock Overflow");
            application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            application.setSize(1200, 800);

            CardLayout cardLayout = new CardLayout();
            JPanel views = new JPanel(cardLayout);
            application.add(views);

            ViewManagerModel viewManagerModel = new ViewManagerModel();
            new ViewManager(views, cardLayout, viewManagerModel);


            LoginViewModel loginViewModel = new LoginViewModel();
            SignupViewModel signupViewModel = new SignupViewModel();
            MainMenuViewModel mainMenuViewModel = new MainMenuViewModel();
            SingleStockViewModel singleStockViewModel = new SingleStockViewModel();
            CreatePortfolioViewModel createPortfolioViewModel = new CreatePortfolioViewModel();
            AddStockViewModel addStockViewModel = new AddStockViewModel();
            SearchViewModel searchViewModel = new SearchViewModel();
            ShowGraphViewModel showGraphViewModel = new ShowGraphViewModel();
            PortfolioAnalysisViewModel portfolioAnalysisViewModel = new PortfolioAnalysisViewModel();
            MonteCarloViewModel monteCarloViewModel = new MonteCarloViewModel();


            FileUserDataAccessObject userDataAccessObject;
            try {

                userDataAccessObject = new FileUserDataAccessObject("./users.csv", new CommonUserFactory());
            } catch (IOException e) {
                throw new RuntimeException("Could not create/open user data file.", e);
            }


            StockDataAccessObject stockDataAccessObject = new StockDataAccessObject();



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


            SignupView signupView = SignupUseCaseFactory.create(
                    viewManagerModel,
                    loginViewModel,
                    signupViewModel,
                    userDataAccessObject
            );
            views.add(signupView, signupView.viewName);


            LoginView loginView = LoginUseCaseFactory.create(
                    viewManagerModel,
                    loginViewModel,
                    mainMenuViewModel,
                    userDataAccessObject
            );
            views.add(loginView, loginView.viewName);


            SingleStockView singleStockView = SingleStockUseCaseFactory.create(
                    viewManagerModel,
                    singleStockViewModel,
                    stockDataAccessObject,
                    monteCarloController,
                    showGraphController
            );
            views.add(singleStockView, singleStockView.viewName);


            MainMenuView mainMenuView = MainMenuUseCaseFactory.create(
                    viewManagerModel,
                    mainMenuViewModel,
                    createPortfolioViewModel,
                    loginViewModel,
                    addStockViewModel,
                    singleStockViewModel
            );
            views.add(mainMenuView, mainMenuView.viewName);


            MainMenuController mainMenuController = mainMenuView.getMainMenuController();


            PortfolioMenuView portfolioMenuView = CreatePortfolioUseCaseFactory.create(
                    viewManagerModel,
                    createPortfolioViewModel,
                    userDataAccessObject,
                    mainMenuController
            );
            views.add(portfolioMenuView, portfolioMenuView.viewName);


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


            SearchView searchView = SearchUseCaseFactory.create(
                    viewManagerModel,
                    searchViewModel,
                    showGraphController,
                    stockDataAccessObject
            );
            views.add(searchView, searchView.viewName);


            ShowGraphView showGraphView = new ShowGraphView(
                    showGraphViewModel,
                    showGraphController,
                    viewManagerModel
            );
            views.add(showGraphView, showGraphView.viewName);

            PortfolioAnalysisView portfolioAnalysisView = new PortfolioAnalysisView(
                    portfolioAnalysisViewModel,
                    portfolioAnalysisController,
                    viewManagerModel
            );
            views.add(portfolioAnalysisView, portfolioAnalysisView.viewName);


            viewManagerModel.setActiveView(loginView.viewName);
            viewManagerModel.firePropertyChanged();

            application.pack();
            application.setLocationRelativeTo(null);
            application.setVisible(true);
        });
    }
}
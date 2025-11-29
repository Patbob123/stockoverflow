package interface_adapter.mainmenu;

import app.*;
import data_access.FileUserDataAccessObject;
import data_access.StockDataAccessObject;
import entities.CommonUserFactory;
import interface_adapter.Login.LoginViewModel;
import interface_adapter.Signup.SignupViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_stock.AddStockViewModel;
import interface_adapter.create_portfolio.CreatePortfolioViewModel;
import interface_adapter.portfolio_analysis.PortfolioAnalysisController;
import interface_adapter.portfolio_analysis.PortfolioAnalysisViewModel;
import interface_adapter.search.SearchViewModel;
import interface_adapter.show_graph.ShowGraphController;
import interface_adapter.show_graph.ShowGraphViewModel;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        JFrame application = new JFrame("Stock Overflow");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        // ViewModels
        LoginViewModel loginViewModel = new LoginViewModel();
        SignupViewModel signupViewModel = new SignupViewModel();
        MainMenuViewModel mainMenuViewModel = new MainMenuViewModel();
        SearchViewModel searchViewModel = new SearchViewModel();
        ShowGraphViewModel showGraphViewModel = new ShowGraphViewModel();
        CreatePortfolioViewModel createPortfolioViewModel = new CreatePortfolioViewModel();
        AddStockViewModel addStockViewModel = new AddStockViewModel();
        PortfolioAnalysisViewModel portfolioAnalysisViewModel = new PortfolioAnalysisViewModel(); // 新增

        // DAOs
        FileUserDataAccessObject userDataAccessObject;
        try {
            userDataAccessObject = new FileUserDataAccessObject("./users.csv", new CommonUserFactory());
        } catch (IOException e) {
            throw new RuntimeException("Could not create/open user data file.", e);
        }
        StockDataAccessObject stockDataAccessObject = new StockDataAccessObject();

        // Controllers
        MainMenuController mainMenuController = new MainMenuController(
                viewManagerModel, searchViewModel, createPortfolioViewModel, addStockViewModel
        );

        ShowGraphController showGraphController = ShowGraphUseCaseFactory.createShowGraphUseCase(
                viewManagerModel, showGraphViewModel, stockDataAccessObject
        );

        PortfolioAnalysisController portfolioAnalysisController = PortfolioAnalysisUseCaseFactory.createController(
                viewManagerModel, portfolioAnalysisViewModel, userDataAccessObject, stockDataAccessObject
        );

        // Views Assembly
        SignupView signupView = SignupUseCaseFactory.create(viewManagerModel, loginViewModel, signupViewModel, userDataAccessObject);
        views.add(signupView, signupView.viewName);

        LoginView loginView = LoginUseCaseFactory.create(viewManagerModel, loginViewModel, mainMenuViewModel, userDataAccessObject);
        views.add(loginView, loginView.viewName);

        MainMenuView mainMenuView = new MainMenuView(mainMenuViewModel, mainMenuController);
        views.add(mainMenuView, mainMenuView.viewName);

        SearchView searchView = new SearchView(searchViewModel, showGraphController, viewManagerModel);
        views.add(searchView, searchView.viewName);

        ShowGraphView showGraphView = ShowGraphUseCaseFactory.create(viewManagerModel, showGraphViewModel, stockDataAccessObject);
        views.add(showGraphView, showGraphView.viewName);

        PortfolioMenuView portfolioMenuView = CreatePortfolioUseCaseFactory.create(
                viewManagerModel, createPortfolioViewModel, userDataAccessObject, mainMenuController
        );
        views.add(portfolioMenuView, portfolioMenuView.viewName);

        AddStockView addStockView = AddStockUseCaseFactory.create(
                viewManagerModel,
                addStockViewModel,
                showGraphController,
                portfolioAnalysisController,
                userDataAccessObject,
                stockDataAccessObject
        );
        views.add(addStockView, addStockView.viewName);

        PortfolioAnalysisView portfolioAnalysisView = PortfolioAnalysisUseCaseFactory.create(
                viewManagerModel, portfolioAnalysisViewModel
        );
        views.add(portfolioAnalysisView, portfolioAnalysisView.viewName);

        // Start
        viewManagerModel.setActiveView(loginView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }
}
package app;

import entities.UserRepository;
import entities.UserSession;
import interface_adapter.ViewManagerModel;
import interface_adapter.change_view.ChangeViewController;
import interface_adapter.change_view.ChangeViewPresenter;
import interface_adapter.create_portfolio.CreatePortfolioViewModel;
import interface_adapter.mainmenu.MainMenuController;
import interface_adapter.mainmenu.MainMenuPresenter;
import interface_adapter.mainmenu.MainMenuViewModel;
import interface_adapter.portfolio.PortfolioMenuController;
import interface_adapter.portfolio.PortfolioMenuPresenter;
import interface_adapter.portfolio.PortfolioMenuViewModel;
import interface_adapter.refresh.RefreshDataController;
import interface_adapter.refresh.RefreshDataPresenter;
import interface_adapter.user.create.CreateAccountController;
import interface_adapter.user.create.CreateAccountPresenter;
import interface_adapter.user.create.CreateAccountViewModel;
import interface_adapter.user.login.LoginController;
import interface_adapter.user.login.LoginPresenter;
import interface_adapter.user.login.LoginViewModel;
import interface_adapter.user.logout.LogoutController;
import interface_adapter.user.logout.LogoutPresenter;
import use_case.change_view.ChangeViewInputBoundary;
import use_case.change_view.ChangeViewInteractor;
import use_case.change_view.ChangeViewOutputBoundary;
import use_case.mainmenu.MainMenuInputBoundary;
import use_case.mainmenu.MainMenuInteractor;
import use_case.mainmenu.MainMenuOutputBoundary;
import use_case.portfolio.PortfolioMenuInputBoundary;
import use_case.portfolio.PortfolioMenuInteractor;
import use_case.portfolio.PortfolioMenuOutputBoundary;
<<<<<<< HEAD:src/java/app/MainMenuBuilder.java
import view.MainMenuView;
import view.SimulationView;
import view.ViewManager;
=======
import use_case.refresh.RefreshDataInteractor;
import use_case.refresh.RefreshDataOutputBoundary;
import use_case.user.create.CreateAccountInteractor;
import use_case.user.create.CreateAccountOutputBoundary;
import use_case.user.login.LoginInteractor;
import use_case.user.login.LoginOutputBoundary;
import use_case.user.logout.LogoutInteractor;
import use_case.user.logout.LogoutOutputBoundary;
import view.*;
>>>>>>> origin/main:src/main/java/app/MainMenuBuilder.java
import entities.Portfolio.PortfolioFactory;

import javax.swing.*;
import java.awt.*;

public class MainMenuBuilder {
    private static final Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final double WIDTH = SCREENSIZE.getWidth();
    public static final double HEIGHT = SCREENSIZE.getHeight();

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private MainMenuViewModel mainMenuViewModel;
    private CreatePortfolioViewModel createPortfolioViewModel;
    private PortfolioMenuViewModel portfolioMenuViewModel;

    private MainMenuView mainMenuView;
    private CreatePortfolioView createPortfolioView;
    private PortfolioMenuView portfolioMenuView;
    private SimulationView simulationView;

    private LoginView loginView;
    private CreateAccountView createAccountView;
    private LoginViewModel loginViewModel;
    private CreateAccountViewModel createAccountViewModel;

    public MainMenuBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public MainMenuBuilder addMainView() {
        mainMenuViewModel = new MainMenuViewModel();
        mainMenuView = new MainMenuView(mainMenuViewModel);
        cardPanel.add(mainMenuView, mainMenuView.getViewName());
        viewManager.addView(mainMenuView.getViewName(), mainMenuView);
        return this;
    }

    public MainMenuBuilder addCreatePortfolioView() {
        createPortfolioViewModel = new CreatePortfolioViewModel();
        createPortfolioView = new CreatePortfolioView(createPortfolioViewModel);
        cardPanel.add(createPortfolioView, createPortfolioView.getViewName());
        viewManager.addView(createPortfolioView.getViewName(), createPortfolioView);
        return this;
    }

    public MainMenuBuilder addPortfolioMenuView() {
        portfolioMenuViewModel = new PortfolioMenuViewModel();
        portfolioMenuView = new PortfolioMenuView(portfolioMenuViewModel);
        cardPanel.add(portfolioMenuView, portfolioMenuView.getViewName());
        viewManager.addView(portfolioMenuView.getViewName(), portfolioMenuView);
        return this;
    }

<<<<<<< HEAD:src/java/app/MainMenuBuilder.java
    public MainMenuBuilder addSimulationView() {
        if (portfolioMenuViewModel == null) {
            portfolioMenuViewModel = new PortfolioMenuViewModel();
        }
        simulationView = new SimulationView(portfolioMenuViewModel);
        cardPanel.add(simulationView, simulationView.getViewName());
        viewManager.addView(simulationView.getViewName(), simulationView);
=======
    public MainMenuBuilder addUserAuthenticationViews() {
        // Create a view model
        loginViewModel = new LoginViewModel();
        createAccountViewModel = new CreateAccountViewModel();

        // Create View
        loginView = new LoginView(loginViewModel);
        createAccountView = new CreateAccountView(createAccountViewModel);

        // Set the references between views
        loginView.setCreateAccountView(createAccountView);
        createAccountView.setLoginView(loginView);

        return this;
    }

    public MainMenuBuilder addUserAuthenticationUseCases() {
        // Obtain the storage instance
        UserRepository userRepository = UserRepository.getInstance();
        UserSession userSession = UserSession.getInstance();

        // create an account use case
        CreateAccountOutputBoundary createAccountOutputBoundary = new CreateAccountPresenter(
                createAccountViewModel, loginViewModel, createAccountView);
        CreateAccountInteractor createAccountInteractor = new CreateAccountInteractor(
                createAccountOutputBoundary, userRepository);
        CreateAccountController createAccountController = new CreateAccountController(
                createAccountInteractor);
        createAccountView.setCreateAccountController(createAccountController);

        LoginOutputBoundary loginOutputBoundary = new LoginPresenter(
                loginViewModel, loginView, mainMenuView);
        LoginInteractor loginInteractor = new LoginInteractor(
                loginOutputBoundary, userRepository, userSession);
        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        loginView.setMainMenuView(mainMenuView);

        LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(
                mainMenuView, loginView);
        LogoutInteractor logoutInteractor = new LogoutInteractor(
                logoutOutputBoundary, userSession);
        LogoutController logoutController = new LogoutController(logoutInteractor);
        mainMenuView.setLogoutController(logoutController);
        mainMenuView.setLoginView(loginView);

        return this;
    }

    public MainMenuBuilder addRefreshDataUseCase() {
        UserSession userSession = UserSession.getInstance();

        // refresh
        RefreshDataOutputBoundary refreshDataOutputBoundary = new RefreshDataPresenter(
                portfolioMenuView);
        RefreshDataInteractor refreshDataInteractor = new RefreshDataInteractor(
                refreshDataOutputBoundary, userSession);
        RefreshDataController refreshDataController = new RefreshDataController(
                refreshDataInteractor);
        portfolioMenuView.setRefreshDataController(refreshDataController);

>>>>>>> origin/main:src/main/java/app/MainMenuBuilder.java
        return this;
    }

    public MainMenuBuilder addMainViewUseCase() {
        final MainMenuOutputBoundary output = new MainMenuPresenter(mainMenuViewModel);
        final MainMenuInputBoundary interactor = new MainMenuInteractor(output);
        final MainMenuController controller = new MainMenuController(interactor);
        mainMenuView.setMainMenuController(controller);
        return this;
    }

    public MainMenuBuilder addChangeViewUseCase() {
        final ChangeViewOutputBoundary output = new ChangeViewPresenter(viewManagerModel);
        final ChangeViewInputBoundary interactor = new ChangeViewInteractor(output);
        final ChangeViewController controller = new ChangeViewController(interactor);

        if (mainMenuView != null) mainMenuView.setChangeViewController(controller);
        if (createPortfolioView != null) createPortfolioView.setChangeViewController(controller);
        if (simulationView != null) simulationView.setChangeViewController(controller);

        return this;
    }

    // Wires up User Story 5 & 9
    public MainMenuBuilder addPortfolioMenuUseCase() {
        final PortfolioMenuOutputBoundary output = new PortfolioMenuPresenter(portfolioMenuViewModel);

        // Create a mock default portfolio for testing
        Portfolio defaultPortfolio = new PortfolioFactory().createPortfolio("My Demo Portfolio");
        // Add some dummy stocks so the graph isn't empty initially
        defaultPortfolio.addStock(new entities.Stock("AAPL", "Apple"));
        defaultPortfolio.addStock(new entities.Stock("GOOG", "Google"));

        final PortfolioMenuInputBoundary interactor = new PortfolioMenuInteractor(output, defaultPortfolio);
        final PortfolioMenuController controller = new PortfolioMenuController(interactor);

        if (portfolioMenuView != null) {
            portfolioMenuView.setPortfolioMenuController(controller);
        }
        if (simulationView != null) {
            simulationView.setPortfolioMenuController(controller);
        }

        // Initialize the view with data from the default portfolio
        portfolioMenuViewModel.getState().setPortfolio(defaultPortfolio);
        portfolioMenuViewModel.firePropertyChange("state");

        return this;
    }

//    public JFrame build() {
//        JFrame application = new JFrame("Stock Overflow");
//        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        // Set the initial view to the login view
//        loginView.setVisible(true);
//        createAccountView.setVisible(false);
//        mainMenuView.setVisible(false);
//        createPortfolioView.setVisible(false);
//        portfolioMenuView.setVisible(false);
//
//        return application;
//    }
//}
    public JFrame build() {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Stockoverflow");
        frame.setSize((int) WIDTH, (int) HEIGHT);
        frame.add(cardPanel);

        viewManagerModel.setActiveView(mainMenuView.getViewName());
        viewManagerModel.firePropertyChange();

        return frame;
    }
}
<<<<<<< HEAD:src/java/app/MainMenuBuilder.java
=======

>>>>>>> origin/main:src/main/java/app/MainMenuBuilder.java

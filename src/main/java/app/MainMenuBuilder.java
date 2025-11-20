package app;

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
import use_case.change_view.ChangeViewInputBoundary;
import use_case.change_view.ChangeViewInteractor;
import use_case.change_view.ChangeViewOutputBoundary;
import use_case.mainmenu.MainMenuInputBoundary;
import use_case.mainmenu.MainMenuInteractor;
import use_case.mainmenu.MainMenuOutputBoundary;
import use_case.portfolio.PortfolioMenuInputBoundary;
import use_case.portfolio.PortfolioMenuInteractor;
import use_case.portfolio.PortfolioMenuOutputBoundary;
import view.CreatePortfolioView;
import view.MainMenuView;
import view.PortfolioMenuView;
import view.ViewManager;
import entities.Portfolio.PortfolioFactory;
import entities.Portfolio.Portfolio;

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
            // Initialize the view with data from the default portfolio
            portfolioMenuViewModel.getState().setPortfolio(defaultPortfolio);
            portfolioMenuViewModel.firePropertyChange("state");
        }
        return this;
    }

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
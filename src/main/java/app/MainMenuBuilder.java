package app;

import data_access.FileStockDataAccessObject;
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
import interface_adapter.simulation.SimulationController;
import interface_adapter.simulation.SimulationPresenter;
import interface_adapter.simulation.SimulationViewModel;
import use_case.change_view.ChangeViewInputBoundary;
import use_case.change_view.ChangeViewInteractor;
import use_case.change_view.ChangeViewOutputBoundary;
import use_case.mainmenu.MainMenuInputBoundary;
import use_case.mainmenu.MainMenuInteractor;
import use_case.mainmenu.MainMenuOutputBoundary;
import use_case.portfolio.PortfolioMenuInputBoundary;
import use_case.portfolio.PortfolioMenuInteractor;
import use_case.portfolio.PortfolioMenuOutputBoundary;
import use_case.simulation.SimulationInputBoundary;
import use_case.simulation.SimulationInteractor;
import use_case.simulation.SimulationOutputBoundary;
import use_case.simulation.StockDataAccessInterface;
import view.CreatePortfolioView;
import view.MainMenuView;
import view.PortfolioMenuView;
import view.SimulationView;
import view.ViewManager;

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
    private MainMenuView mainMenuView;
    private CreatePortfolioViewModel createPortfolioViewModel;
    private CreatePortfolioView createPortfolioView;
    private PortfolioMenuViewModel portfolioMenuViewModel;
    private PortfolioMenuView portfolioMenuView;

    // Simulation Components
    private SimulationViewModel simulationViewModel;
    private SimulationView simulationView;
    private StockDataAccessInterface stockDAO;

    public MainMenuBuilder() {
        cardPanel.setLayout(cardLayout);
        stockDAO = new FileStockDataAccessObject(); // Initialize DAO
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

        // Wire up Portfolio Use Case
        PortfolioMenuOutputBoundary portfolioPresenter = new PortfolioMenuPresenter(portfolioMenuViewModel);
        // Note: Passing null for Portfolio initially as it might be set later or not needed for global market analysis
        PortfolioMenuInputBoundary portfolioInteractor = new PortfolioMenuInteractor(portfolioPresenter, null, stockDAO);
        PortfolioMenuController portfolioController = new PortfolioMenuController(portfolioInteractor);
        portfolioMenuView.setPortfolioMenuController(portfolioController);

        return this;
    }

    public MainMenuBuilder addSimulationView() {
        simulationViewModel = new SimulationViewModel();
        simulationView = new SimulationView(simulationViewModel);
        cardPanel.add(simulationView, simulationView.getViewName());
        viewManager.addView(simulationView.getViewName(), simulationView);

        // Wire up Simulation Use Case
        SimulationOutputBoundary simulationPresenter = new SimulationPresenter(simulationViewModel, viewManagerModel);
        SimulationInputBoundary simulationInteractor = new SimulationInteractor(stockDAO, simulationPresenter);
        SimulationController simulationController = new SimulationController(simulationInteractor);
        simulationView.setSimulationController(simulationController);

        return this;
    }

    public MainMenuBuilder addMainViewUseCase() {
        final MainMenuOutputBoundary mainMenuOutputBoundary = new MainMenuPresenter(mainMenuViewModel);
        final MainMenuInputBoundary mainMenuInteractor = new MainMenuInteractor(mainMenuOutputBoundary);

        final MainMenuController mainMenuController = new MainMenuController(mainMenuInteractor);
        mainMenuView.setMainMenuController(mainMenuController);
        return this;
    }

    public MainMenuBuilder addChangeViewUseCase() {
        final ChangeViewOutputBoundary changeViewOutputBoundary = new ChangeViewPresenter(viewManagerModel);
        final ChangeViewInputBoundary changeViewInteractor = new ChangeViewInteractor(changeViewOutputBoundary);

        final ChangeViewController changeViewController = new ChangeViewController(changeViewInteractor);

        mainMenuView.setChangeViewController(changeViewController);
        createPortfolioView.setChangeViewController(changeViewController);

        if (simulationView != null) {
            simulationView.setChangeViewController(changeViewController);
        }

        return this;
    }


    /**
     * Builds the application.
     * @return the JFrame for the application
     */
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

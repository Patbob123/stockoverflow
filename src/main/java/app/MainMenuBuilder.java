package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_view.ChangeViewController;
import interface_adapter.change_view.ChangeViewPresenter;
import interface_adapter.create_portfolio.CreatePortfolioViewModel;
import interface_adapter.mainmenu.MainMenuController;
import interface_adapter.mainmenu.MainMenuPresenter;
import interface_adapter.mainmenu.MainMenuViewModel;
import interface_adapter.portfolio.PortfolioMenuViewModel;
import use_case.change_view.ChangeViewInputBoundary;
import use_case.change_view.ChangeViewInteractor;
import use_case.change_view.ChangeViewOutputBoundary;
import use_case.mainmenu.MainMenuInputBoundary;
import use_case.mainmenu.MainMenuInteractor;
import use_case.mainmenu.MainMenuOutputBoundary;
import view.CreatePortfolioView;
import view.MainMenuView;
import view.PortfolioMenuView;
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
    // private NoteDataAccessInterface noteDAO;
    private MainMenuViewModel mainMenuViewModel;
    private MainMenuView mainMenuView;
    private CreatePortfolioViewModel createPortfolioViewModel;
    private CreatePortfolioView createPortfolioView;
    private PortfolioMenuViewModel portfolioMenuViewModel;
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

        // refresh so that the note will be visible when we start the program
        //noteInteractor.executeRefresh();

        return frame;

    }

}


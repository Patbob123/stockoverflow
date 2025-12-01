package app;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import data_access.*;
import entities.CommonUserFactory;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.portfolio.PortfolioMenuController;
import interface_adapter.portfolio.PortfolioMenuPresenter;
import interface_adapter.portfolio.PortfolioMenuState;
import interface_adapter.portfolio.addStock.AddStockMenuController;
import interface_adapter.portfolio.addStock.AddStockMenuPresenter;
import interface_adapter.portfolio.addStock.AddStockMenuViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.singlestock.SingleStockController;
import interface_adapter.singlestock.SingleStockPresenter;
import interface_adapter.singlestock.SingleStockViewInterface;
import interface_adapter.singlestock.SingleStockViewModel;
import use_case.UserDataAccessInterface;
import use_case.import_export.ImportExportDataAccessInterface;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_portfolio.AddPortfolioController;
import interface_adapter.add_portfolio.AddPortfolioPresenter;
import interface_adapter.add_portfolio.AddPortfolioViewModel;
import interface_adapter.change_view.ChangeViewController;
import interface_adapter.change_view.ChangeViewPresenter;
import interface_adapter.change_view.ChangeViewState;
import interface_adapter.import_export.ImportExportController;
import interface_adapter.import_export.ImportExportPresenter;
import interface_adapter.import_export.ImportExportViewModel;
import interface_adapter.mainmenu.MainMenuController;
import interface_adapter.mainmenu.MainMenuPresenter;
import interface_adapter.mainmenu.MainMenuViewModel;
import interface_adapter.portfolio.PortfolioMenuViewModel;
import use_case.add_portfolio.AddPortfolioInteractor;
import use_case.change_view.ChangeViewInputBoundary;
import use_case.change_view.ChangeViewInteractor;
import use_case.change_view.ChangeViewOutputBoundary;
import use_case.import_export.ImportExportInteractor;
import use_case.login.LoginInteractor;
import use_case.mainmenu.MainMenuInteractor;
import use_case.portfolio.PortfolioMenuInteractor;
import use_case.portfolio.addStock.AddStockMenuInteractor;
import use_case.signup.SignupInteractor;
import use_case.singlestock.AnalyzeSingleStockInteractor;
import use_case.singlestock.CompareTwoStocksInteractor;
import use_case.singlestock.RiskFreeRateDataAccessInterface;
import use_case.singlestock.StockPriceDataAccessInterface;
import view.*;
import app.wrapper.UseCaseWrapper;
import app.wrapper.ViewViewModelBuilderWrapper;

public class MainMenuBuilder {

    private static final Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final double WIDTH = SCREENSIZE.getWidth();
    public static final double HEIGHT = SCREENSIZE.getHeight();

    private final ArrayList<ViewViewModelBuilderWrapper<?, ?>> viewBuildInstruction = new ArrayList<>();
    private final ArrayList<UseCaseWrapper<?, ?, ?, ?, ?>> useCaseBuildInstruction = new ArrayList<>();
    private Boolean addChangeView = false;
    private Boolean addSingleStock = false;
    private Boolean addLogin = false;
    private Boolean addSignup = false;

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);
    private ImportExportDataAccessInterface importExportDAO;
    private StockPriceDataAccessInterface stockPriceDAO;
    private RiskFreeRateDataAccessInterface riskFreeRateDAO;
    private UserDataAccessInterface userDAO;

    private final ViewViewModelBuilderWrapper<MainMenuViewModel, MainMenuView> mainMenu =
            new ViewViewModelBuilderWrapper<>(new MainMenuViewModel(), MainMenuView::new);

    private final ViewViewModelBuilderWrapper<ImportExportViewModel, ImportExportView> importExportMenu =
            new ViewViewModelBuilderWrapper<>(new ImportExportViewModel(), ImportExportView::new);

    private ViewViewModelBuilderWrapper<PortfolioMenuViewModel, PortfolioMenuView> portFolioMenu =
            new ViewViewModelBuilderWrapper<>(new PortfolioMenuViewModel(new PortfolioMenuState()), PortfolioMenuView::new);

    private final ViewViewModelBuilderWrapper<AddPortfolioViewModel, AddPortfolioView> addPortfolioMenu =
            new ViewViewModelBuilderWrapper<>(new AddPortfolioViewModel(), AddPortfolioView::new);

    private final ViewViewModelBuilderWrapper<AddStockMenuViewModel, AddStockMenuView> addStockMenu =
            new ViewViewModelBuilderWrapper<>(new AddStockMenuViewModel(), AddStockMenuView::new);

    private final ViewViewModelBuilderWrapper<SingleStockViewModel, SingleStockView> singleStockMenu =
            new ViewViewModelBuilderWrapper<>(new SingleStockViewModel(), SingleStockView::new);

    private final ViewViewModelBuilderWrapper<LoginViewModel, LoginView> loginView =
            new ViewViewModelBuilderWrapper<>(new LoginViewModel(), LoginView::new);

    private final ViewViewModelBuilderWrapper<SignupViewModel, SignupView> signupView =
            new ViewViewModelBuilderWrapper<>(new SignupViewModel(), SignupView::new);

    private final UseCaseWrapper<MainMenuInteractor,
            MainMenuPresenter,
            MainMenuController,
            MainMenuViewModel,
            MainMenuView> mainmenuUsecase =
            new UseCaseWrapper<>(viewManager,
            MainMenuPresenter::new,
            MainMenuInteractor::new,
            MainMenuController::new, MainMenuView.VIEW_NAME);

    private final UseCaseWrapper<ImportExportInteractor,
            ImportExportPresenter,
            ImportExportController,
            ImportExportViewModel,
            ImportExportView> importExportUsecase =
            new UseCaseWrapper<>(viewManager,
            ImportExportPresenter::new, presenter -> new ImportExportInteractor(presenter, importExportDAO),
            ImportExportController::new, ImportExportView.VIEW_NAME);

    private final UseCaseWrapper<AddPortfolioInteractor,
            AddPortfolioPresenter,
            AddPortfolioController,
            AddPortfolioViewModel,
            AddPortfolioView> addPortfolioUsecase =
            new UseCaseWrapper<>(viewManager,
            AddPortfolioPresenter::new,
            AddPortfolioInteractor::new,
            AddPortfolioController::new, AddPortfolioView.VIEW_NAME);

    private final UseCaseWrapper<PortfolioMenuInteractor,
            PortfolioMenuPresenter,
            PortfolioMenuController,
            PortfolioMenuViewModel,
            PortfolioMenuView> portfolioMenuUsecase =
            new UseCaseWrapper<>(viewManager,
            PortfolioMenuPresenter::new,
            PortfolioMenuInteractor::new,
            PortfolioMenuController::new, PortfolioMenuView.VIEW_NAME);

    private final UseCaseWrapper<AddStockMenuInteractor,
            AddStockMenuPresenter,
            AddStockMenuController,
            AddStockMenuViewModel,
            AddStockMenuView> addStockMenuUsecase =
            new UseCaseWrapper<>(viewManager,
            AddStockMenuPresenter::new,
            AddStockMenuInteractor::new,
            AddStockMenuController::new, AddStockMenuView.VIEW_NAME);


    private PaddedView<?, ?> getView(String viewName) {
        return viewManager.getViews().get(viewName);
    }

    public MainMenuBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public MainMenuBuilder addMainView() {
        viewBuildInstruction.add(mainMenu);
        return this;
    }

    public MainMenuBuilder addImportExportView() {
        viewBuildInstruction.add(importExportMenu);
        return this;
    }

    public MainMenuBuilder addPortfolioMenuView() {
        viewBuildInstruction.add(portFolioMenu);
        return this;
    }

    public MainMenuBuilder addAddPortfolioView() {
        viewBuildInstruction.add(addPortfolioMenu);
        return this;
    }

    public MainMenuBuilder addStockMenuView() {
        viewBuildInstruction.add(addStockMenu);
        return this;
    }

    public MainMenuBuilder addSingleStockView() {
        viewBuildInstruction.add(singleStockMenu);
        return this;
    }

    public MainMenuBuilder addLoginView() {
        viewBuildInstruction.add(loginView);
        return this;
    }

    public MainMenuBuilder addSignupView() {
        viewBuildInstruction.add(signupView);
        return this;
    }

    public MainMenuBuilder addSingleStockUseCase() {
        this.addSingleStock = true;
        return this;
    }

    public MainMenuBuilder addLoginUseCase() {
        this.addLogin = true;
        return this;
    }

    public MainMenuBuilder addChangeViewUseCase() {
        this.addChangeView = true;
        return this;
    }

    public MainMenuBuilder addSignupUseCase() {
        this.addSignup = true;
        return this;
    }

    private void addSignUpUseCaseDirectly() {
        final SignupView view = (SignupView) getView(SignupView.VIEW_NAME);
        final SignupPresenter presenter = new SignupPresenter(loginView.getView().getViewModel(), view.getViewModel(), viewManagerModel);

        final SignupInteractor signupInteractor = new SignupInteractor(userDAO, presenter, new CommonUserFactory());
        final SignupController controller =
                new SignupController(signupInteractor);

        view.setController(controller);
    }

    private void addLoginUseCaseDirectly() {
        final LoginView view = (LoginView) getView(LoginView.VIEW_NAME);
        final LoginPresenter presenter = new LoginPresenter(mainMenu.getView().getViewModel(), view.getViewModel(), viewManagerModel);

        final LoginInteractor loginInteractor = new LoginInteractor(userDAO, presenter);
        final LoginController controller =
                new LoginController(loginInteractor);

        view.setController(controller);
    }

    private void addSingleStockUseCaseDirectly() {
        final SingleStockView view = (SingleStockView) getView(SingleStockView.VIEW_NAME);
        final SingleStockPresenter presenter = new SingleStockPresenter(view);

        final AnalyzeSingleStockInteractor analyzeInteractor =
                new AnalyzeSingleStockInteractor(stockPriceDAO, riskFreeRateDAO, presenter);

        final CompareTwoStocksInteractor compareInteractor =
                new CompareTwoStocksInteractor(stockPriceDAO, riskFreeRateDAO, presenter);

        final SingleStockController controller =
                new SingleStockController(analyzeInteractor, compareInteractor);

        view.setController(controller);
    }

    private void addChangeViewUseCaseDirectly() {
        final ChangeViewState changeViewState = new ChangeViewState();
        final ChangeViewOutputBoundary changeViewOutputBoundary = new ChangeViewPresenter(viewManagerModel);
        final ChangeViewInputBoundary changeViewInteractor = new ChangeViewInteractor(changeViewOutputBoundary, changeViewState);

        final ChangeViewController changeViewController = new ChangeViewController(changeViewInteractor);

        getView(MainMenuView.VIEW_NAME).setChangeViewController(changeViewController);
        getView(ImportExportView.VIEW_NAME).setChangeViewController(changeViewController);
        getView(AddPortfolioView.VIEW_NAME).setChangeViewController(changeViewController);
        getView(PortfolioMenuView.VIEW_NAME).setChangeViewController(changeViewController);
        getView(AddStockMenuView.VIEW_NAME).setChangeViewController(changeViewController);
        getView(LoginView.VIEW_NAME).setChangeViewController(changeViewController);
    }

    public MainMenuBuilder addMainViewUseCase() {
        useCaseBuildInstruction.add(mainmenuUsecase);
        return this;
    }

    public MainMenuBuilder addImportExportUseCase() {
        useCaseBuildInstruction.add(importExportUsecase);
        return this;
    }

    public MainMenuBuilder addPortfolioUseCase() {
        useCaseBuildInstruction.add(addPortfolioUsecase);
        return this;
    }

    public MainMenuBuilder addPortfolioMenuUseCase() {
        useCaseBuildInstruction.add(portfolioMenuUsecase);
        return this;
    }

    public MainMenuBuilder addStockMenuUseCase() {
        useCaseBuildInstruction.add(addStockMenuUsecase);
        return this;
    }

    public MainMenuBuilder addSingleStockDAO() {
        String alphaKey = "7d3793701beaa71a8263c3ae2d4a508b"; // or System.getenv("ALPHAVANTAGE_API_KEY");

        final StockPriceDataAccessInterface stooqGateway =
                new StooqStockDataAccess();
        final StockPriceDataAccessInterface alphaGateway =
                new AlphaVantageStockPriceDataAccess(alphaKey);

        String fredKey = System.getenv("7d3793701beaa71a8263c3ae2d4a508b");


        stockPriceDAO = new CombinedStockPriceDataAccess(stooqGateway, alphaGateway);
        riskFreeRateDAO = new FredRiskFreeRateDataAccess(fredKey);

        return this;
    }

    public MainMenuBuilder addUserDAO() {
        try {
            userDAO = new FileUserDataAccessObject("./users.csv", new CommonUserFactory());
        }
        catch (IOException ioException) {
            throw new RuntimeException("Could not create/open user data file.", ioException);
        }
        return this;
    }

    public JFrame autoBuild() {
        return this
                .addLoginView()
                .addSignupView()
                .addMainView()
                .addImportExportView()
                .addPortfolioMenuView()
                .addAddPortfolioView()
                .addStockMenuView()
                .addSingleStockView()
                .addChangeViewUseCase()
                .addMainViewUseCase()
                .addPortfolioUseCase()
                .addImportExportUseCase()
                .addPortfolioMenuUseCase()
                .addStockMenuUseCase()
                .addSingleStockUseCase()
                .addLoginUseCase()
                .addSignupUseCase()
                .addSingleStockDAO()
                .addUserDAO()
                .build();
    }

    public JFrame build() {

        for (ViewViewModelBuilderWrapper<?, ?> viewBuilder : this.viewBuildInstruction) {
            viewBuilder.addView(this, cardPanel, viewManager);
        }
        viewBuildInstruction.clear();
        if (addChangeView) {
            this.addChangeViewUseCaseDirectly();
        }
        if (addSingleStock) {
            this.addSingleStockUseCaseDirectly();
        }
        if (addLogin) {
            this.addLoginUseCaseDirectly();
        }
        if(addSignup){
            this.addSignUpUseCaseDirectly();
        }
        for (UseCaseWrapper<?, ?, ?, ?, ?> usecaseBuilder : this.useCaseBuildInstruction) {
            usecaseBuilder.build(this);
        }
        useCaseBuildInstruction.clear();
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Stockoverflow");
        frame.setSize((int) WIDTH, (int) HEIGHT);

        frame.add(cardPanel);

        if (viewManager.getViews().containsKey(PortfolioMenuView.VIEW_NAME)) {
            ((PortfolioMenuState) viewManager.getViews().get(PortfolioMenuView.VIEW_NAME)
                    .getViewModel().getState()).setStockPriceDataAccess(stockPriceDAO);
        }

        viewManagerModel.setActiveView(getView(LoginView.VIEW_NAME).getViewName());
        viewManagerModel.firePropertyChange();

        return frame;
    }
}
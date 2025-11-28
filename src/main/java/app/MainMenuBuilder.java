package app;

import java.awt.*;

import javax.swing.*;

import interface_adapter.ViewModel;
import interface_adapter.portfolio.PortfolioMenuController;
import interface_adapter.portfolio.PortfolioMenuPresenter;
import interface_adapter.portfolio.addStock.AddStockMenuController;
import interface_adapter.portfolio.addStock.AddStockMenuPresenter;
import interface_adapter.portfolio.addStock.AddStockMenuViewModel;
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
import use_case.add_portfolio.AddPortfolioInputBoundary;
import use_case.add_portfolio.AddPortfolioInteractor;
import use_case.add_portfolio.AddPortfolioOutputBoundary;
import use_case.change_view.ChangeViewInputBoundary;
import use_case.change_view.ChangeViewInteractor;
import use_case.change_view.ChangeViewOutputBoundary;
import use_case.import_export.ImportExportInputBoundary;
import use_case.import_export.ImportExportInteractor;
import use_case.import_export.ImportExportOutputBoundary;
import use_case.mainmenu.MainMenuInputBoundary;
import use_case.mainmenu.MainMenuInteractor;
import use_case.mainmenu.MainMenuOutputBoundary;
import use_case.portfolio.PortfolioMenuInteractor;
import use_case.portfolio.addStock.AddStockMenuInteractor;
import view.*;
import view.wrapper.UseCaseWrapper;
import view.wrapper.ViewViewModelBuilderWrapper;

public class MainMenuBuilder {

    private static final Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final double WIDTH = SCREENSIZE.getWidth();
    public static final double HEIGHT = SCREENSIZE.getHeight();

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);
    private ImportExportDataAccessInterface importExportDAO;

    private final ViewViewModelBuilderWrapper<MainMenuViewModel, MainMenuView> mainMenu =
            new ViewViewModelBuilderWrapper<>(new MainMenuViewModel(), MainMenuView::new);

    private final ViewViewModelBuilderWrapper<ImportExportViewModel, ImportExportView> importExportMenu =
            new ViewViewModelBuilderWrapper<>(new ImportExportViewModel(), ImportExportView::new);

    private final ViewViewModelBuilderWrapper<PortfolioMenuViewModel, PortfolioMenuView> portFolioMenu =
            new ViewViewModelBuilderWrapper<>(new PortfolioMenuViewModel(), PortfolioMenuView::new);

    private final ViewViewModelBuilderWrapper<AddPortfolioViewModel, AddPortfolioView> addPortfolioMenu =
            new ViewViewModelBuilderWrapper<>(new AddPortfolioViewModel(), AddPortfolioView::new);

    private final ViewViewModelBuilderWrapper<AddStockMenuViewModel, AddStockMenuView> addStockMenu =
            new ViewViewModelBuilderWrapper<>(new AddStockMenuViewModel(), AddStockMenuView::new);

    private final UseCaseWrapper<MainMenuInteractor,
            MainMenuPresenter,
            MainMenuController,
            MainMenuViewModel,
            MainMenuView> mainmenuUsecase = new UseCaseWrapper<>();

    private final UseCaseWrapper<ImportExportInteractor,
            ImportExportPresenter,
            ImportExportController,
            ImportExportViewModel,
            ImportExportView> importExportUsecase = new UseCaseWrapper<>();

    private final UseCaseWrapper<AddPortfolioInteractor,
            AddPortfolioPresenter,
            AddPortfolioController,
            AddPortfolioViewModel,
            AddPortfolioView> addPortfolioUsecase = new UseCaseWrapper<>();

    private final UseCaseWrapper<PortfolioMenuInteractor,
            PortfolioMenuPresenter,
            PortfolioMenuController,
            PortfolioMenuViewModel,
            PortfolioMenuView> portfolioMenuUsecase = new UseCaseWrapper<>();

    private final UseCaseWrapper<AddStockMenuInteractor,
            AddStockMenuPresenter,
            AddStockMenuController,
            AddStockMenuViewModel,
            AddStockMenuView> addStockMenuUsecase = new UseCaseWrapper<>();

    private PaddedView<?, ?> getView(String viewName) {
        return viewManager.getViews().get(viewName);
    }

    public MainMenuBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public MainMenuBuilder addMainView() {
        return mainMenu.addView(this, cardPanel, viewManager);
    }

    public MainMenuBuilder addImportExportView() {
        return importExportMenu.addView(this, cardPanel, viewManager);
    }

    public MainMenuBuilder addPortfolioMenuView() {
        return portFolioMenu.addView(this, cardPanel, viewManager);
    }

    public MainMenuBuilder addAddPortfolioView() {
        return addPortfolioMenu.addView(this, cardPanel, viewManager);
    }

    public MainMenuBuilder addStockMenuView() {
        return addStockMenu.addView(this, cardPanel, viewManager);
    }

    public MainMenuBuilder addChangeViewUseCase() {
        final ChangeViewState changeViewState = new ChangeViewState();
        final ChangeViewOutputBoundary changeViewOutputBoundary = new ChangeViewPresenter(viewManagerModel);
        final ChangeViewInputBoundary changeViewInteractor = new ChangeViewInteractor(changeViewOutputBoundary, changeViewState);

        final ChangeViewController changeViewController = new ChangeViewController(changeViewInteractor);

        getView(MainMenuView.VIEW_NAME).setChangeViewController(changeViewController);
        getView(ImportExportView.VIEW_NAME).setChangeViewController(changeViewController);
        getView(AddPortfolioView.VIEW_NAME).setChangeViewController(changeViewController);
        getView(PortfolioMenuView.VIEW_NAME).setChangeViewController(changeViewController);
        getView(AddStockMenuView.VIEW_NAME).setChangeViewController(changeViewController);
        return this;
    }

    public MainMenuBuilder addMainViewUseCase() {
        return mainmenuUsecase.useCaseWrapper(this,
                viewManager,
                MainMenuPresenter::new,
                MainMenuInteractor::new,
                MainMenuController::new, MainMenuView.VIEW_NAME);
    }

    public MainMenuBuilder addImportExportUseCase() {
        return importExportUsecase.useCaseWrapper(this,
                viewManager,
                ImportExportPresenter::new,
                presenter -> new ImportExportInteractor(presenter, importExportDAO),
                ImportExportController::new, ImportExportView.VIEW_NAME);
    }

    public MainMenuBuilder addAddPortfolioUseCase() {
        return addPortfolioUsecase.useCaseWrapper(this,
                viewManager,
                AddPortfolioPresenter::new,
                AddPortfolioInteractor::new,
                AddPortfolioController::new, AddPortfolioView.VIEW_NAME);
    }

    public MainMenuBuilder addPortfolioMenuUseCase() {
        return portfolioMenuUsecase.useCaseWrapper(this,
                viewManager,
                PortfolioMenuPresenter::new,
                PortfolioMenuInteractor::new,
                PortfolioMenuController::new, PortfolioMenuView.VIEW_NAME);
    }

    public MainMenuBuilder addStockMenuUseCase() {
        return addStockMenuUsecase.useCaseWrapper(this,
                viewManager,
                AddStockMenuPresenter::new,
                AddStockMenuInteractor::new,
                AddStockMenuController::new, AddStockMenuView.VIEW_NAME);
    }

    public JFrame build() {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Stockoverflow");
        frame.setSize((int) WIDTH, (int) HEIGHT);

        frame.add(cardPanel);

        viewManagerModel.setActiveView(getView(MainMenuView.VIEW_NAME).getViewName());
        viewManagerModel.firePropertyChange();

        return frame;
    }
}
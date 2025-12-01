package interface_adapter.import_export;

import entities.Portfolio.Portfolio;
import interface_adapter.ViewManagerModel;
import interface_adapter.portfolio.PortfolioMenuState;
import interface_adapter.portfolio.PortfolioMenuViewModel;
import use_case.import_export.ImportExportOutputBoundary;
import view.PortfolioMenuView;

public class ImportExportPresenter implements ImportExportOutputBoundary {
    private final ImportExportViewModel importExportViewModel;
    private final PortfolioMenuViewModel portfolioMenuViewModel;
    private final ViewManagerModel viewManagerModel;

    public ImportExportPresenter(ImportExportViewModel importExportViewModel,
                                 PortfolioMenuViewModel portfolioMenuViewModel,
                                 ViewManagerModel viewManagerModel) {
        this.importExportViewModel = importExportViewModel;
        this.portfolioMenuViewModel = portfolioMenuViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(Portfolio portfolio) {
        PortfolioMenuState state = portfolioMenuViewModel.getState();
        state.setPortfolio(portfolio);
        portfolioMenuViewModel.setState(state);
        portfolioMenuViewModel.firePropertyChange();

        viewManagerModel.setActiveView(PortfolioMenuView.VIEW_NAME);
        viewManagerModel.firePropertyChange();

        importExportViewModel.firePropertyChange();
    }
}
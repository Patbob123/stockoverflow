package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.portfolio_analysis.PortfolioAnalysisController;
import interface_adapter.portfolio_analysis.PortfolioAnalysisPresenter;
import interface_adapter.portfolio_analysis.PortfolioAnalysisViewModel;
import use_case.APIDataAccessInterface;
import use_case.UserDataAccessInterface;
import use_case.portfolio_analysis.PortfolioAnalysisInputBoundary;
import use_case.portfolio_analysis.PortfolioAnalysisInteractor;
import use_case.portfolio_analysis.PortfolioAnalysisOutputBoundary;
import view.PortfolioAnalysisView;

public class PortfolioAnalysisUseCaseFactory {
    private PortfolioAnalysisUseCaseFactory() {}


    public static PortfolioAnalysisView create(
            ViewManagerModel viewManagerModel,
            PortfolioAnalysisViewModel viewModel) {
        return new PortfolioAnalysisView(viewModel, viewManagerModel);
    }


    public static PortfolioAnalysisController createController(
            ViewManagerModel viewManagerModel,
            PortfolioAnalysisViewModel viewModel,
            UserDataAccessInterface userDataAccess,
            APIDataAccessInterface apiDataAccess) {

        PortfolioAnalysisOutputBoundary outputBoundary = new PortfolioAnalysisPresenter(viewModel, viewManagerModel);
        PortfolioAnalysisInputBoundary interactor = new PortfolioAnalysisInteractor(userDataAccess, apiDataAccess, outputBoundary);

        return new PortfolioAnalysisController(interactor);
    }
}
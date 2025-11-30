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

import javax.swing.*;

public class PortfolioAnalysisUseCaseFactory {

    private PortfolioAnalysisUseCaseFactory() {}

    public static PortfolioAnalysisController createPortfolioAnalysisUseCase(
            ViewManagerModel viewManagerModel,
            PortfolioAnalysisViewModel portfolioAnalysisViewModel,
            UserDataAccessInterface userDataAccessInterface,
            APIDataAccessInterface apiDataAccessInterface) {

        try {
            PortfolioAnalysisOutputBoundary outputBoundary = new PortfolioAnalysisPresenter(viewManagerModel, portfolioAnalysisViewModel);

            PortfolioAnalysisInputBoundary interactor = new PortfolioAnalysisInteractor(
                    userDataAccessInterface,
                    apiDataAccessInterface,
                    outputBoundary
            );

            return new PortfolioAnalysisController(interactor);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error creating Portfolio Analysis Use Case.");
            return null;
        }
    }
}
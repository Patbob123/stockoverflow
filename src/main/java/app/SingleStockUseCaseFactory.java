package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.monte_carlo.MonteCarloController;
import interface_adapter.show_graph.ShowGraphController;
import interface_adapter.singlestock.SingleStockController;
import interface_adapter.singlestock.SingleStockPresenter;
import interface_adapter.singlestock.SingleStockViewModel;
import use_case.APIDataAccessInterface;
import use_case.singlestock.AnalyzeSingleStockInputBoundary;
import use_case.singlestock.AnalyzeSingleStockInteractor;
import use_case.singlestock.AnalyzeSingleStockOutputBoundary;
import use_case.singlestock.CompareTwoStocksInputBoundary;
import use_case.singlestock.CompareTwoStocksInteractor;
import use_case.singlestock.CompareTwoStocksOutputBoundary;
import view.SingleStockView;

import javax.swing.*;
import java.io.IOException;

public class SingleStockUseCaseFactory {

    private SingleStockUseCaseFactory() {}

    public static SingleStockView create(
            ViewManagerModel viewManagerModel,
            SingleStockViewModel singleStockViewModel,
            APIDataAccessInterface apiDataAccessObject,
            MonteCarloController monteCarloController,
            ShowGraphController showGraphController) {

        try {
            SingleStockController singleStockController = createSingleStockUseCase(
                    viewManagerModel,
                    singleStockViewModel,
                    apiDataAccessObject,
                    monteCarloController,
                    showGraphController
            );

            return new SingleStockView(singleStockController, singleStockViewModel, viewManagerModel);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open user data file.");
        }

        return null;
    }

    private static SingleStockController createSingleStockUseCase(
            ViewManagerModel viewManagerModel,
            SingleStockViewModel singleStockViewModel,
            APIDataAccessInterface apiDataAccessObject,
            MonteCarloController monteCarloController,
            ShowGraphController showGraphController) throws IOException {

        SingleStockPresenter presenter = new SingleStockPresenter(singleStockViewModel, viewManagerModel);

        AnalyzeSingleStockInputBoundary analyzeInteractor = new AnalyzeSingleStockInteractor(
                apiDataAccessObject,
                (AnalyzeSingleStockOutputBoundary) presenter
        );

        CompareTwoStocksInputBoundary compareInteractor = new CompareTwoStocksInteractor(
                apiDataAccessObject,
                (CompareTwoStocksOutputBoundary) presenter
        );

        return new SingleStockController(
                analyzeInteractor,
                compareInteractor,
                monteCarloController,
                showGraphController
        );
    }
}
package interface_adapter.singlestock;

import use_case.singlestock.AnalyzeSingleStockInputBoundary;
import use_case.singlestock.AnalyzeSingleStockInputData;
import use_case.singlestock.CompareTwoStocksInputBoundary;
import use_case.singlestock.CompareTwoStocksInputData;

import interface_adapter.monte_carlo.MonteCarloController;
import interface_adapter.show_graph.ShowGraphController;

public class SingleStockController {

    private final AnalyzeSingleStockInputBoundary analyzeInteractor;
    private final CompareTwoStocksInputBoundary compareInteractor;


    private final MonteCarloController monteCarloController;
    private final ShowGraphController showGraphController;

    public SingleStockController(AnalyzeSingleStockInputBoundary analyzeInteractor,
                                 CompareTwoStocksInputBoundary compareInteractor,
                                 MonteCarloController monteCarloController,
                                 ShowGraphController showGraphController) {
        this.analyzeInteractor = analyzeInteractor;
        this.compareInteractor = compareInteractor;
        this.monteCarloController = monteCarloController;
        this.showGraphController = showGraphController;
    }


    public void analyze(String ticker, double rfAnnual) {
        AnalyzeSingleStockInputData input = new AnalyzeSingleStockInputData(ticker, rfAnnual);
        analyzeInteractor.execute(input);
    }


    public void compare(String ticker1, String ticker2, double rfAnnual) {
        CompareTwoStocksInputData input = new CompareTwoStocksInputData(ticker1, ticker2, rfAnnual);
        compareInteractor.execute(input);
    }


    public void runMonteCarlo(String ticker) {
        if (monteCarloController != null) {
            monteCarloController.execute(ticker, 1000, 252);
        } else {
            System.err.println("MonteCarloController not initialized.");
        }
    }


    public void showGraph(String ticker) {
        if (showGraphController != null) {
            showGraphController.execute(ticker, SingleStockViewModel.VIEW_NAME);
        } else {
            System.err.println("ShowGraphController not initialized.");
        }
    }
}
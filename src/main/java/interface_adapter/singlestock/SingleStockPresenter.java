package main.java.interface_adapter.singlestock;

import main.java.use_case.singlestock.AnalyzeSingleStockOutputBoundary;
import main.java.use_case.singlestock.AnalyzeSingleStockOutputData;
import main.java.use_case.singlestock.CompareTwoStocksOutputBoundary;
import main.java.use_case.singlestock.CompareTwoStocksOutputData;

public class SingleStockPresenter
        implements AnalyzeSingleStockOutputBoundary, CompareTwoStocksOutputBoundary {

    private final SingleStockViewInterface view;

    public SingleStockPresenter(SingleStockViewInterface view) {
        this.view = view;
    }

    @Override
    public void present(AnalyzeSingleStockOutputData outputData) {
        view.showAnalysis(outputData);
    }

    @Override
    public void present(CompareTwoStocksOutputData outputData) {

        String combinedTicker =
                outputData.getTicker1() + " vs " + outputData.getTicker2();

        AnalyzeSingleStockOutputData wrapped =
                new AnalyzeSingleStockOutputData(
                        combinedTicker,
                        outputData.getRiskFreeAnnual(),
                        outputData.getReport()
                );
        view.showAnalysis(wrapped);
    }

    //@Override
    public void presentError(String message) {
        view.showError(message);
    }
}

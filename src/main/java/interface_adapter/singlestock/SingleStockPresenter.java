package interface_adapter.singlestock;

import interface_adapter.ViewManagerModel;
import use_case.singlestock.AnalyzeSingleStockOutputBoundary;
import use_case.singlestock.AnalyzeSingleStockOutputData;
import use_case.singlestock.CompareTwoStocksOutputBoundary;
import use_case.singlestock.CompareTwoStocksOutputData;

public class SingleStockPresenter
        implements AnalyzeSingleStockOutputBoundary, CompareTwoStocksOutputBoundary {

    private final SingleStockViewModel singleStockViewModel;
    private final ViewManagerModel viewManagerModel;

    public SingleStockPresenter(SingleStockViewModel singleStockViewModel,
                                ViewManagerModel viewManagerModel) {
        this.singleStockViewModel = singleStockViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void present(AnalyzeSingleStockOutputData outputData) {
        SingleStockState state = singleStockViewModel.getState();

        state.setReport(outputData.getReport());
        state.setCurrentTicker(outputData.getTicker());
        state.setCurrentRiskFree(String.valueOf(outputData.getRiskFreeAnnual()));
        state.setErrorMessage(null);

        singleStockViewModel.setState(state);

        singleStockViewModel.firePropertyChanged();
    }

    @Override
    public void present(CompareTwoStocksOutputData outputData) {
        SingleStockState state = singleStockViewModel.getState();

        state.setReport(outputData.getReport());
        state.setCurrentTicker(outputData.getTicker1() + " / " + outputData.getTicker2());
        state.setCurrentRiskFree(String.valueOf(outputData.getRiskFreeAnnual()));
        state.setErrorMessage(null);

        singleStockViewModel.setState(state);
        singleStockViewModel.firePropertyChanged();
    }
}
package interface_adapter.singlestock;

import use_case.singlestock.AnalyzeSingleStockOutputBoundary;
import use_case.singlestock.AnalyzeSingleStockOutputData;

public class SingleStockPresenter implements AnalyzeSingleStockOutputBoundary {

    private final SingleStockViewInterface view;

    public SingleStockPresenter(SingleStockViewInterface view) {
        this.view = view;
    }

    @Override
    public void present(AnalyzeSingleStockOutputData outputData) {
        view.showAnalysis(outputData);
    }
}

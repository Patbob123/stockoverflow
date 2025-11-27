package interface_adapter.singlestock;

import use_case.singlestock.AnalyzeSingleStockOutputData;

public interface SingleStockViewInterface {
    void showAnalysis(AnalyzeSingleStockOutputData outputData);
    void showError(String message);
}

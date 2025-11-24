package main.java.interface_adapter.singlestock;

import main.java.use_case.singlestock.AnalyzeSingleStockOutputData;

public interface SingleStockViewInterface {
    void showAnalysis(AnalyzeSingleStockOutputData outputData);
    void showError(String message);
}

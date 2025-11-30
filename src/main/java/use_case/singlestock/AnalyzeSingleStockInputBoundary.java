package use_case.singlestock;

import use_case.InputBoundary;

public interface AnalyzeSingleStockInputBoundary extends InputBoundary {
    void execute(AnalyzeSingleStockInputData inputData);
}
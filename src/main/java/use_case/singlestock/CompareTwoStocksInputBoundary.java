package use_case.singlestock;

import use_case.InputBoundary;

public interface CompareTwoStocksInputBoundary extends InputBoundary {
    void execute(CompareTwoStocksInputData inputData);
}

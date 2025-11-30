package use_case.singlestock;

import use_case.OutputBoundary;

public interface CompareTwoStocksOutputBoundary extends OutputBoundary {
    void present(CompareTwoStocksOutputData outputData);
}
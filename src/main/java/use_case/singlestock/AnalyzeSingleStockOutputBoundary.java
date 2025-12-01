package use_case.singlestock;

import use_case.OutputBoundary;

public interface AnalyzeSingleStockOutputBoundary extends OutputBoundary {
    void present(AnalyzeSingleStockOutputData outputData);
}

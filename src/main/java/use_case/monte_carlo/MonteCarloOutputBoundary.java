package use_case.monte_carlo;

import use_case.OutputBoundary;

public interface MonteCarloOutputBoundary extends OutputBoundary {
    void presentSuccess(MonteCarloOutputData outputData);
    void presentError(String message);
}
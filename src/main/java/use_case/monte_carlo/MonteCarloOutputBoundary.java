package use_case.monte_carlo;

public interface MonteCarloOutputBoundary {
    void presentSuccess(MonteCarloOutputData outputData);
    void presentError(String message);
}
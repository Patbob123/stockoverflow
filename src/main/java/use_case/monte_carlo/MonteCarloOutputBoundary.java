package use_case.monte_carlo;

public interface MonteCarloOutputBoundary {
    void prepareSuccessView(MonteCarloOutputData outputData);
    void prepareFailView(String error);
}

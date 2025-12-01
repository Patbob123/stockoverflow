package use_case.monte_carlo;

import entities.monte_carlo.MonteCarloSimulation;

import java.util.List;

public interface MonteCarloOutputBoundary {
    void presentSuccess(MonteCarloOutputData outputData);
    void presentHistorySuccess(List<MonteCarloSimulation> history);
    void presentError(String message);
}
package use_case.monte_carlo;

import use_case.InputBoundary;

public interface MonteCarloInputBoundary extends InputBoundary {
    void execute(MonteCarloInputData inputData);
}
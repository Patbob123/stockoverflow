package use_case.historical_simulation;

public interface HistoricalSimulationOutputBoundary {
    void prepareSuccessView(HistoricalSimulationOutputData outputData);
    void prepareFailView(String error);
}
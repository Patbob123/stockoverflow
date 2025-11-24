package use_case.simulation;

public interface SimulationOutputBoundary {
    void prepareSuccessView(SimulationOutputData outputData);
    void prepareFailView(String error);
}

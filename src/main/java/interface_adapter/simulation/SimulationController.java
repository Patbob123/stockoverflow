package interface_adapter.simulation;

import use_case.simulation.SimulationInputBoundary;
import use_case.simulation.SimulationInputData;

public class SimulationController {
    private final SimulationInputBoundary userUseCase;

    public SimulationController(SimulationInputBoundary userUseCase) {
        this.userUseCase = userUseCase;
    }

    public void execute(String ticker, int numPaths, double timeHorizon) {
        SimulationInputData inputData = new SimulationInputData(ticker, numPaths, timeHorizon);
        userUseCase.execute(inputData);
    }
}

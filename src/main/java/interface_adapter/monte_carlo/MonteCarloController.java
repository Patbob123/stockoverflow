package interface_adapter.monte_carlo;

import use_case.monte_carlo.MonteCarloInputBoundary;
import use_case.monte_carlo.MonteCarloInputData;

public class MonteCarloController {
    final MonteCarloInputBoundary interactor;

    public MonteCarloController(MonteCarloInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String ticker, int simulationCount, int timeHorizon) {
        MonteCarloInputData inputData = new MonteCarloInputData(ticker, simulationCount, timeHorizon);
        interactor.execute(inputData);
    }
}

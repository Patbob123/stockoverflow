package interface_adapter.monte_carlo;

import interface_adapter.AbsController;
import use_case.monte_carlo.MonteCarloInputBoundary;
import use_case.monte_carlo.MonteCarloInputData;

/**
 * The Controller receives input from the View (UI) and converts it into a format
 * suitable for the Use Case layer (Input Data), then triggers the Interactor.
 * This class belongs to the Interface Adapters Layer.
 */
public class MonteCarloController extends AbsController {

    private final MonteCarloInputBoundary interactor;

    public MonteCarloController(MonteCarloInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Receives the raw user input and packages it for the Interactor.
     */
    public void executeSimulation(String ticker, double horizonYears, int nSteps, int nPaths) {
        // 1. Package the raw input into a data structure
        MonteCarloInputData input = new MonteCarloInputData(
                ticker,
                horizonYears,
                nSteps,
                nPaths
        );

        // 2. Delegate to the Interactor (Use Case)
        interactor.execute(input);
    }
}
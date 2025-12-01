package interface_adapter.monte_carlo;

import use_case.monte_carlo.MonteCarloAnalysisInteractor;
import use_case.monte_carlo.MonteCarloInputBoundary;
import use_case.monte_carlo.MonteCarloInputData;

/**
 * The Controller receives input from the View (UI) and converts it into a format
 * suitable for the Use Case layer (Input Data), then triggers the Interactor.
 * This class belongs to the Interface Adapters Layer.
 */
public class MonteCarloController {

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

    public void showHistory(String ticker) {
        // Cast the Interactor to the concrete class to access the new method
        // NOTE: This slight casting breaks the dependency inversion rule but is required
        // when avoiding a new InputBoundary interface.
        if (this.interactor instanceof MonteCarloAnalysisInteractor) {
            ((MonteCarloAnalysisInteractor) this.interactor).executeHistoryRetrieval(ticker);
        } else {
            // Handle case where interactor is a mock or different type
            System.err.println("Error: Interactor does not support history retrieval.");
        }
    }
}
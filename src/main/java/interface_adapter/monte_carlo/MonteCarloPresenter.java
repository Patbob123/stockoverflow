package interface_adapter.monte_carlo;

import use_case.monte_carlo.MonteCarloOutputBoundary;
import use_case.monte_carlo.MonteCarloOutputData;
import view.monte_carlo.MonteCarloView;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * The Presenter implements the Output Boundary for the Monte Carlo Use Case.
 * It takes raw data from the Interactor (Output Data) and formats it into
 * presentation-ready strings and models for the View.
 */
public class MonteCarloPresenter implements MonteCarloOutputBoundary {

    private final MonteCarloView view;
    private final NumberFormat currencyFormat;

    /**
     * The Presenter is injected with its View (the UI contract).
     */
    public MonteCarloPresenter(MonteCarloView view) {
        this.view = view;
        this.currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
    }

    /**
     * Success case: Receives raw simulation results and formats them for display.
     */
    @Override
    public void presentSuccess(MonteCarloOutputData outputData) {
        // 1. Format Metrics (Presentation Logic)
        String initialPriceStr = currencyFormat.format(outputData.getInitialPrice());
        String expectedTerminalPriceStr = currencyFormat.format(outputData.getMeanTerminalPrice());

        // 2. Tell the View to Display Metrics
        view.displayMetrics(initialPriceStr, expectedTerminalPriceStr);

        // 3. Tell the View to Draw the Chart
        // We decide how many paths to show here (e.g., 50 paths)
        view.showPaths(outputData.getPaths(), 50, "Monte Carlo Simulation");
    }

    /**
     * Error case: Forwards the error message to the View.
     */
    @Override
    public void presentError(String message) {
        view.showErrorMessage(message);
    }
}
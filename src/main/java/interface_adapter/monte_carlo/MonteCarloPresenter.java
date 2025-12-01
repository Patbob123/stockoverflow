package interface_adapter.monte_carlo;

import entities.monte_carlo.MonteCarloSimulation;
import use_case.monte_carlo.MonteCarloOutputBoundary;
import use_case.monte_carlo.MonteCarloOutputData;
import view.monte_carlo.MonteCarloView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * The Presenter implements the Output Boundary for the Monte Carlo Use Case.
 * It takes raw data from the Interactor (Output Data) and formats it into
 * a single presentation-ready object (the ViewModel) for the View.
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
     * Success case: Receives raw simulation results, formats them into a ViewModel,
     * and signals the View to display the result.
     */
    @Override
    public void presentSuccess(MonteCarloOutputData outputData) {
        // 1. Format Metrics (Presentation Logic)
        String initialPriceStr = currencyFormat.format(outputData.getInitialPrice());
        String expectedTerminalPriceStr = currencyFormat.format(outputData.getMeanTerminalPrice());


        // We decide here how many paths to display
        final int N_PATHS_TO_SHOW = 50;

        // 2. Construct the ViewModel
        MonteCarloViewModel viewModel = new MonteCarloViewModel(
                outputData.getPaths(),
                "Monte Carlo Simulation Paths" ,
                "Initial Price: " + initialPriceStr,
                "Expected Terminal Price: " + expectedTerminalPriceStr,
                N_PATHS_TO_SHOW
        );

        // 3. Deliver the ViewModel to the View
        view.showSuccessView(viewModel);
    }

    public void presentHistorySuccess(List<MonteCarloSimulation> history) {
        // This is where you format the list and pass it to the Swing View for display.

        // Example: Compile a simple string report
        StringBuilder report = new StringBuilder();
        report.append("--- Monte Carlo History for ").append(history.get(0).getTicker()).append(" ---\n");
        report.append(String.format("%-25s %-15s %-10s %s\n", "Timestamp", "Expected Price", "Paths", "ID (Partial)"));

        for (MonteCarloSimulation sim : history) {
            report.append(String.format("%-25s $%-14.2f %-10d %s\n",
                    sim.getTimestamp().toLocalDate(),
                    sim.getExpectedTerminalPrice(),
                    sim.getNPaths(),
                    sim.getId().substring(0, 8) + "..." // Show partial ID
            ));
        }

        view.displayHistory(report.toString());
    }

    /**
     * Error case: Forwards the error message to the View.
     */
    @Override
    public void presentError(String message) {
        view.showErrorMessage(message);
    }
}
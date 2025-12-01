package view.monte_carlo;

import interface_adapter.monte_carlo.MonteCarloViewModel;

public interface MonteCarloView {

    /**
     * Shows the results of a successful simulation by passing a single
     * formatted ViewModel object that contains all display data.
     */
    void showSuccessView(MonteCarloViewModel viewModel);

    void displayHistory(String history);

    /**
     * Shows the error view with the given message.
     */
    void showErrorMessage(String message);
}
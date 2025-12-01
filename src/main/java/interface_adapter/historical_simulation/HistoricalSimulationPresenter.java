package interface_adapter.historical_simulation;

import use_case.OutputBoundary;
import use_case.historical_simulation.HistoricalSimulationOutputBoundary;
import use_case.historical_simulation.HistoricalSimulationOutputData;

public class HistoricalSimulationPresenter implements HistoricalSimulationOutputBoundary, OutputBoundary {
    private final HistoricalSimulationViewModel viewModel;

    public HistoricalSimulationPresenter(HistoricalSimulationViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(HistoricalSimulationOutputData data) {
        HistoricalSimulationState state = viewModel.getState();

        String result = String.format(
                "<html><h3>Simulation Results</h3>" +
                        "From: %s<br/>" +
                        "Initial Portfolio Value: $%.2f<br/>" +
                        "Current Portfolio Value: $%.2f<br/>" +
                        "Total Return: <font color='%s'>%.2f%%</font></html>",
                data.getStartDateStr(),
                data.getInitialValue(),
                data.getCurrentValue(),
                data.getTotalReturn() >= 0 ? "green" : "red",
                data.getTotalReturn() * 100
        );

        state.setResultText(result);
        state.setError(null);
        viewModel.setState(state);
        viewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        HistoricalSimulationState state = viewModel.getState();
        state.setError(error);
        viewModel.firePropertyChange();
    }
}
package interface_adapter.monte_carlo;

import use_case.monte_carlo.MonteCarloOutputBoundary;
import use_case.monte_carlo.MonteCarloOutputData;

public class MonteCarloPresenter implements MonteCarloOutputBoundary {
    private final MonteCarloViewModel viewModel;

    public MonteCarloPresenter(MonteCarloViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(MonteCarloOutputData outputData) {
        MonteCarloState state = viewModel.getState();
        state.setTicker(outputData.getTicker());
        state.setSimulationPaths(outputData.getSimulationPaths());
        state.setError(null);

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        MonteCarloState state = viewModel.getState();
        state.setError(error);
        viewModel.firePropertyChanged();
    }
}
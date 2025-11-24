package interface_adapter.simulation;

import use_case.simulation.SimulationOutputBoundary;
import use_case.simulation.SimulationOutputData;
import interface_adapter.ViewManagerModel;

public class SimulationPresenter implements SimulationOutputBoundary {
    private final SimulationViewModel simulationViewModel;
    private final ViewManagerModel viewManagerModel;

    public SimulationPresenter(SimulationViewModel simulationViewModel, ViewManagerModel viewManagerModel) {
        this.simulationViewModel = simulationViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(SimulationOutputData outputData) {
        SimulationState state = simulationViewModel.getState();
        state.setTicker(outputData.getTicker());
        state.setSimulationPaths(outputData.getPaths());
        state.setError(null);

        simulationViewModel.setState(state);
        simulationViewModel.firePropertyChange();

        // Ensure we stay on the same view, or if this was triggered from elsewhere, switch to it.
        // Usually we are already on the view.
    }

    @Override
    public void prepareFailView(String error) {
        SimulationState state = simulationViewModel.getState();
        state.setError(error);
        simulationViewModel.setState(state);
        simulationViewModel.firePropertyChange();
    }
}

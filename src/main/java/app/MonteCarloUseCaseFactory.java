package app;

import interface_adapter.monte_carlo.MonteCarloController;
import interface_adapter.monte_carlo.MonteCarloPresenter;
import interface_adapter.monte_carlo.MonteCarloViewModel;
import use_case.APIDataAccessInterface;
import use_case.monte_carlo.MonteCarloInputBoundary;
import use_case.monte_carlo.MonteCarloInteractor;
import use_case.monte_carlo.MonteCarloOutputBoundary;

public class MonteCarloUseCaseFactory {
    private MonteCarloUseCaseFactory() {}

    public static MonteCarloController create(
            MonteCarloViewModel viewModel,
            APIDataAccessInterface apiDataAccess) {

        MonteCarloOutputBoundary presenter = new MonteCarloPresenter(viewModel);
        MonteCarloInputBoundary interactor = new MonteCarloInteractor(apiDataAccess, presenter);

        return new MonteCarloController(interactor);
    }
}

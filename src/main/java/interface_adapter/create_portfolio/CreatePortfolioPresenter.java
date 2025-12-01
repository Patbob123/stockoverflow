package interface_adapter.create_portfolio;

import use_case.create_portfolio.CreatePortfolioOutputBoundary;
import use_case.create_portfolio.CreatePortfolioOutputData;
import interface_adapter.ViewManagerModel;

public class CreatePortfolioPresenter implements CreatePortfolioOutputBoundary {
    private final CreatePortfolioViewModel viewModel;

    public CreatePortfolioPresenter(CreatePortfolioViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(CreatePortfolioOutputData response) {
        CreatePortfolioState state = viewModel.getState();
        state.setPortfolioName(response.getPortfolioName());
        state.setError(null);
        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        CreatePortfolioState state = viewModel.getState();
        state.setError(error);
        viewModel.firePropertyChanged();
    }
}
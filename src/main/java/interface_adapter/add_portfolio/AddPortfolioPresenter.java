package interface_adapter.add_portfolio;

import use_case.add_portfolio.AddPortfolioOutputBoundary;
import use_case.add_portfolio.AddPortfolioOutputData;

public class AddPortfolioPresenter implements AddPortfolioOutputBoundary {
    private final AddPortfolioViewModel addPortfolioViewModel;

    public AddPortfolioPresenter(AddPortfolioViewModel addPortfolioViewModel) {
        this.addPortfolioViewModel = addPortfolioViewModel;
    }

    @Override
    public void prepareNavigationView(AddPortfolioOutputData outputData) {
        final AddPortfolioState state = addPortfolioViewModel.getState();
        state.setTargetView(outputData.getTargetViewName());
        addPortfolioViewModel.setState(state);
        addPortfolioViewModel.firePropertyChange();
    }
}
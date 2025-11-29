package interface_adapter.create_portfolio;

import use_case.create_portfolio.CreatePortfolioOutputBoundary;

public class CreatePortfolioPresenter implements CreatePortfolioOutputBoundary {
    private final CreatePortfolioViewModel createPortfolioViewModel;

    public CreatePortfolioPresenter(CreatePortfolioViewModel createPortfolioViewModel) {
        this.createPortfolioViewModel = createPortfolioViewModel;
    }

    @Override
    public void prepareSuccessView(String message) {
        System.out.println("interface_adapter.mainmenu.Main menu did something idk what dont ask me");
        createPortfolioViewModel.firePropertyChange();
    }
}

package use_case.add_portfolio;

public class AddPortfolioInteractor implements AddPortfolioInputBoundary {
    private final AddPortfolioOutputBoundary addPortfolioPresenter;

    public AddPortfolioInteractor(AddPortfolioOutputBoundary addPortfolioPresenter) {
        this.addPortfolioPresenter = addPortfolioPresenter;
    }
}
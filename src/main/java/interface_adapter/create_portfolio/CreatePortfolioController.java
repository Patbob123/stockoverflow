package interface_adapter.create_portfolio;

import use_case.create_portfolio.CreatePortfolioInputBoundary;
import use_case.create_portfolio.CreatePortfolioInputData;

public class CreatePortfolioController {
    final CreatePortfolioInputBoundary interactor;

    public CreatePortfolioController(CreatePortfolioInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String username, String portfolioName) {
        CreatePortfolioInputData data = new CreatePortfolioInputData(username, portfolioName);
        interactor.execute(data);
    }
}

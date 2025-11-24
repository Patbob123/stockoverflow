package interface_adapter.add_portfolio;

import use_case.add_portfolio.AddPortfolioInputBoundary;

public class AddPortfolioController {
    private final AddPortfolioInputBoundary addPortfolioInputBoundary;

    public AddPortfolioController(AddPortfolioInputBoundary addPortfolioInputBoundary) {
        this.addPortfolioInputBoundary = addPortfolioInputBoundary;
    }

}

package interface_adapter.add_portfolio;

import interface_adapter.AbsController;
import use_case.add_portfolio.AddPortfolioInputBoundary;

public class AddPortfolioController extends AbsController {
    private final AddPortfolioInputBoundary addPortfolioInputBoundary;

    public AddPortfolioController(AddPortfolioInputBoundary addPortfolioInputBoundary) {

        this.addPortfolioInputBoundary = addPortfolioInputBoundary;
    }

}

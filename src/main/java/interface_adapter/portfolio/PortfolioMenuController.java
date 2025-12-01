package interface_adapter.portfolio;

import interface_adapter.AbsController;
import lombok.Getter;
import use_case.portfolio.PortfolioMenuInputBoundary;

public class PortfolioMenuController extends AbsController {
    @Getter
    private final PortfolioMenuInputBoundary portfolioMenuInputBoundary;

    public PortfolioMenuController(PortfolioMenuInputBoundary portfolioMenuInputBoundary) {
        this.portfolioMenuInputBoundary = portfolioMenuInputBoundary;
    }

    /**
     * Executes the Note related Use Cases.
     * @param note the note to be recorded
     */
}

package interface_adapter.portfolio;

import lombok.Getter;
import use_case.portfolio.PortfolioMenuInputBoundary;

public class PortfolioMenuController {
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

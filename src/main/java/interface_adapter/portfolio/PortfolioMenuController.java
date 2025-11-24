package interface_adapter.portfolio;

import lombok.Getter;
import use_case.portfolio.PortfolioMenuInputBoundary;
import java.util.ArrayList;
import java.util.List;

public class PortfolioMenuController {
    @Getter
    private final PortfolioMenuInputBoundary portfolioMenuInputBoundary;

    public PortfolioMenuController(PortfolioMenuInputBoundary portfolioMenuInputBoundary) {
        this.portfolioMenuInputBoundary = portfolioMenuInputBoundary;
    }

    public void executeGraph(List<String> tickers) {
        portfolioMenuInputBoundary.executeGraph(tickers);
    }

    public void executeAnalysis(int days) {
        portfolioMenuInputBoundary.executeHistoricalAnalysis(days);
    }
}

    /**
     * Executes the Note related Use Cases.
     * @param note the note to be recorded
     */


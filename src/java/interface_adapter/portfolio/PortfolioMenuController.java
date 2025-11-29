package interface_adapter.portfolio;

import use_case.portfolio.PortfolioMenuInputBoundary;
import java.util.ArrayList;
import java.util.List;

public class PortfolioMenuController {
    private final PortfolioMenuInputBoundary portfolioMenuInputBoundary;

    public PortfolioMenuController(PortfolioMenuInputBoundary portfolioMenuInputBoundary) {
        this.portfolioMenuInputBoundary = portfolioMenuInputBoundary;
    }

    public PortfolioMenuInputBoundary getPortfolioMenuInputBoundary() {
        return portfolioMenuInputBoundary;
    }

    public void executeGraph(List<String> tickers) {
        portfolioMenuInputBoundary.executeGraph(tickers);
    }

    public void executeAnalysis(int days) {
        portfolioMenuInputBoundary.executeHistoricalAnalysis(days);
    }

    public void execute(String action) {
        if ("exit".equals(action)) {
            portfolioMenuInputBoundary.executeExit();
        }
    }
}

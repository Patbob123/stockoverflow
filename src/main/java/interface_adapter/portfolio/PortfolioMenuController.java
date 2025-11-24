package interface_adapter.portfolio;

import lombok.Getter;
import use_case.portfolio.PortfolioMenuInputBoundary;
import java.util.List;

public class PortfolioMenuController {
    @Getter
    private final PortfolioMenuInputBoundary portfolioMenuInputBoundary;

    public PortfolioMenuController(PortfolioMenuInputBoundary portfolioMenuInputBoundary) {
        this.portfolioMenuInputBoundary = portfolioMenuInputBoundary;
    }

    public void executeLoadMarketData() {
        portfolioMenuInputBoundary.executeLoadMarketData();
    }

    public void executeAnalyze(List<String> tickers) {
        portfolioMenuInputBoundary.executeAnalyze(tickers);
    }
}

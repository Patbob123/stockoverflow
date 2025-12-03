package interface_adapter.portfolio;

import entities.Portfolio.Portfolio;
import interface_adapter.AbsController;
import lombok.Getter;
import lombok.Setter;
import use_case.portfolio.PortfolioInputData;
import use_case.portfolio.PortfolioMenuInputBoundary;
import use_case.portfolio.PortfolioOutputData;

import java.util.Comparator;

public class PortfolioMenuController extends AbsController {
    @Getter
    private final PortfolioMenuInputBoundary portfolioMenuInputBoundary;
    @Getter
    @Setter
    private PortfolioInputData portfolioInputData;
    @Getter
    @Setter
    private PortfolioOutputData portfolioOutputData;

    public PortfolioMenuController(PortfolioMenuInputBoundary portfolioMenuInputBoundary, PortfolioInputData portfolioInputData, PortfolioOutputData portfolioOutputData) {
        this.portfolioMenuInputBoundary = portfolioMenuInputBoundary;
        this.portfolioInputData = portfolioInputData;
        this.portfolioOutputData = portfolioOutputData;
    }

    public PortfolioMenuController(PortfolioMenuInputBoundary portfolioMenuInputBoundary) {
        this.portfolioMenuInputBoundary = portfolioMenuInputBoundary;
        this.portfolioInputData = null;
        this.portfolioOutputData = null;
    }

    public Portfolio getPortfolio() {
        return portfolioMenuInputBoundary.getPortfolio();
    }

    public void sortPortfolio(Portfolio portfolio, String method) {
        portfolioMenuInputBoundary.sortPortfolio(portfolio, method);
    }
}

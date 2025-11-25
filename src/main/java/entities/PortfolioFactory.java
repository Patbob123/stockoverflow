package entities;

public class PortfolioFactory {

    public Portfolio createPortfolio(String portfolioName) {
        return new Portfolio(portfolioName);
    }
}
package data_access;

import entities.Portfolio.Portfolio;

public interface PortfolioDataAccessInterface {
    Portfolio getPortfolio(String portfolioId);
    void savePortfolio(Portfolio portfolio);
}
package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PortfolioList {
    private List<Portfolio> portfolios;

    public PortfolioList() {
        this.portfolios = new ArrayList<>();
    }

    public void addPortfolio(Portfolio portfolio) {
        portfolios.add(portfolio);
    }

    public void removePortfolio(String portfolioName) {
        portfolios.removeIf(p -> p.getName().equals(portfolioName));
    }

    public Optional<Portfolio> getPortfolio(String portfolioName) {
        return portfolios.stream()
                .filter(p -> p.getName().equals(portfolioName))
                .findFirst();
    }

    public List<Portfolio> getAllPortfolios() {
        return new ArrayList<>(portfolios); // ret copy
    }

    public boolean containsPortfolio(String portfolioName) {
        return portfolios.stream().anyMatch(p -> p.getName().equals(portfolioName));
    }
}

package entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PortfolioList implements Iterable<Portfolio> {
    private List<Portfolio> portfolios;

    public PortfolioList() {
        this.portfolios = new ArrayList<>();
    }

    public void addPortfolio(Portfolio portfolio) {
        this.portfolios.add(portfolio);
    }

    public void removePortfolio(String portfolioName) {
        portfolios.removeIf(p -> p.getName().equals(portfolioName));
    }

    public Portfolio getPortfolio(String name) {
        for (Portfolio p : portfolios) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    public List<Portfolio> getPortfolios() {
        return portfolios;
    }

    @Override
    public Iterator<Portfolio> iterator() {
        return portfolios.iterator();
    }
}
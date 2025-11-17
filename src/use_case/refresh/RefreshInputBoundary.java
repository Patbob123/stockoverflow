package use_case.refresh;

import entities.Portfolio;
import entities.Stock;

public interface RefreshInputBoundary {
    void refreshStock(String ticker, Stock currentStock);
    void refreshPortfolio(Portfolio portfolio);
}
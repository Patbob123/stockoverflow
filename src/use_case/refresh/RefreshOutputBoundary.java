package use_case.refresh;

import entities.Portfolio;
import entities.Stock;

public interface RefreshOutputBoundary {
    void prepareStockSuccessView(Stock stock);
    void preparePortfolioSuccessView(Portfolio portfolio);
    void prepareUpToDateView(String name);
    void prepareFailView(String errorMessage);
}

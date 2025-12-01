package use_case.create_portfolio;

import use_case.InputBoundary;

public interface CreatePortfolioInputBoundary extends InputBoundary {
    void execute(CreatePortfolioInputData createPortfolioInputData);
}
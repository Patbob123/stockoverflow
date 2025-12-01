package use_case.create_portfolio;

import use_case.OutputBoundary;

public interface CreatePortfolioOutputBoundary extends OutputBoundary {
    void prepareSuccessView(CreatePortfolioOutputData portfolio);
    void prepareFailView(String error);
}
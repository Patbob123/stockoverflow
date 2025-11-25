package use_case.create_portfolio;

public interface CreatePortfolioOutputBoundary {
    void prepareSuccessView(CreatePortfolioOutputData portfolio);
    void prepareFailView(String error);
}
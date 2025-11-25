package use_case.remove_stock;

import entities.Portfolio;
import entities.User;
import use_case.UserDataAccessInterface;

public class RemoveStockInteractor implements RemoveStockInputBoundary {
    private final UserDataAccessInterface userDataAccess;
    private final RemoveStockOutputBoundary outputBoundary;

    public RemoveStockInteractor(UserDataAccessInterface userDataAccess,
                                 RemoveStockOutputBoundary outputBoundary) {
        this.userDataAccess = userDataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(RemoveStockInputData inputData) {
        User user = userDataAccess.get(inputData.getUsername());
        if (user == null) {
            outputBoundary.prepareFailView("User not found.");
            return;
        }

        Portfolio portfolio = user.getPortfolioList().getPortfolio(inputData.getPortfolioName());
        if (portfolio == null) {
            outputBoundary.prepareFailView("Portfolio not found.");
            return;
        }

        String ticker = inputData.getTicker();
        if (portfolio.getStocks().containsKey(ticker)) {
            portfolio.removeStock(ticker);
            userDataAccess.save(user);
            outputBoundary.prepareSuccessView(new RemoveStockOutputData(ticker + " removed successfully."));
        } else {
            outputBoundary.prepareFailView("Stock " + ticker + " not found in portfolio.");
        }
    }
}
package use_case.add_stock;

import entities.Portfolio;
import entities.Stock;
import entities.User;
import use_case.APIDataAccessInterface;
import use_case.UserDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class AddStockInteractor implements AddStockInputBoundary {
    private final UserDataAccessInterface userDataAccess;
    private final APIDataAccessInterface apiDataAccess;
    private final AddStockOutputBoundary outputBoundary;

    public AddStockInteractor(UserDataAccessInterface userDataAccess,
                              APIDataAccessInterface apiDataAccess,
                              AddStockOutputBoundary outputBoundary) {
        this.userDataAccess = userDataAccess;
        this.apiDataAccess = apiDataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(AddStockInputData inputData) {
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

        List<String> successfullyAdded = new ArrayList<>();
        StringBuilder errors = new StringBuilder();

        for (String ticker : inputData.getTickers()) {
            // 1. Check if stock already exists in portfolio
            if (portfolio.getStocks().containsKey(ticker)) {
                errors.append(ticker).append(" already exists. ");
                continue;
            }

            // 2. Validate stock via API
            try {
                Stock stock = apiDataAccess.getStock(ticker);
                if (stock != null) {
                    portfolio.addStock(stock);
                    successfullyAdded.add(ticker);
                } else {
                    errors.append("Ticker ").append(ticker).append(" not found. ");
                }
            } catch (Exception e) {
                errors.append("Error fetching ").append(ticker).append(". ");
            }
        }

        // 3. Save changes
        if (!successfullyAdded.isEmpty()) {
            userDataAccess.save(user);
            outputBoundary.prepareSuccessView(new AddStockOutputData(
                    portfolio.getName(),
                    successfullyAdded,
                    false,
                    "Added: " + successfullyAdded + ". " + errors
            ));
        } else {
            outputBoundary.prepareFailView("No stocks added. " + errors);
        }
    }
}
package use_case.create_portfolio;

import entities.Portfolio;
import entities.User;
import use_case.UserDataAccessInterface;

public class CreatePortfolioInteractor implements CreatePortfolioInputBoundary {
    final UserDataAccessInterface userDataAccessObject;
    final CreatePortfolioOutputBoundary userPresenter;

    public CreatePortfolioInteractor(UserDataAccessInterface userDataAccessInterface,
                                     CreatePortfolioOutputBoundary createPortfolioOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.userPresenter = createPortfolioOutputBoundary;
    }

    @Override
    public void execute(CreatePortfolioInputData createPortfolioInputData) {
        String username = createPortfolioInputData.getUsername();
        String portfolioName = createPortfolioInputData.getPortfolioName();

        User user = userDataAccessObject.get(username);

        if (user.getPortfolioList().getPortfolio(portfolioName) != null) {
            userPresenter.prepareFailView("Portfolio already exists.");
        } else {

            Portfolio newPortfolio = new Portfolio(portfolioName);
            user.getPortfolioList().addPortfolio(newPortfolio);

            userDataAccessObject.save(user);

            CreatePortfolioOutputData outputData = new CreatePortfolioOutputData(portfolioName, false);
            userPresenter.prepareSuccessView(outputData);
        }
    }
}
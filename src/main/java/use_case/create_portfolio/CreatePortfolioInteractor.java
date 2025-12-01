package use_case.create_portfolio;

import entities.Portfolio.Portfolio;
import entities.Portfolio.PortfolioFactory;
import entities.User;
import interface_adapter.create_portfolio.CreatePortfolioState;
import use_case.UserDataAccessInterface;

import java.util.List;

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

            PortfolioFactory portfolioFactory = new PortfolioFactory();
            Portfolio newPortfolio = portfolioFactory.createPortfolio(portfolioName);
            user.getPortfolioList().addPortfolio(newPortfolio);

            userDataAccessObject.save(user);



            CreatePortfolioOutputData outputData = new CreatePortfolioOutputData(portfolioName, false);
            userPresenter.prepareSuccessView(outputData);
        }
    }
}
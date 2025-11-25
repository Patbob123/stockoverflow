package use_case.refresh;

import entities.Portfolio.Portfolio;
import entities.UserSession;
import entities.PortfolioList;
import java.time.LocalDateTime;
import java.util.Random;

public class RefreshDataInteractor implements RefreshDataInputBoundary {
    private final RefreshDataOutputBoundary outputBoundary;
    private final UserSession userSession;
    private LocalDateTime lastRefreshTime;
    private final Random random = new Random();

    public RefreshDataInteractor(RefreshDataOutputBoundary outputBoundary, UserSession userSession) {
        this.outputBoundary = outputBoundary;
        this.userSession = userSession;
        this.lastRefreshTime = LocalDateTime.now().minusMinutes(5); // 初始设置为5分钟前
    }

    @Override
    public void execute(String portfolioName) {
        // Check whether the user is logged in
        if (!userSession.isUserLoggedIn()) {
            outputBoundary.prepareFailureView("please log in first");
            return;
        }

        // Check whether the combination exists
        PortfolioList portfolioList = userSession.getCurrentUser().getPortfolioList();
        if (portfolioList == null) {
            outputBoundary.prepareFailureView("No investment portfolio");
            return;
        }

        Portfolio portfolio = portfolioList.getPortfolio(portfolioName);
        if (portfolio == null) {
            outputBoundary.prepareFailureView("Cannot be" + portfolioName + "portfolio");
            return;
        }

        // Simulated data refresh
        LocalDateTime now = LocalDateTime.now();
        boolean hasNewData = random.nextBoolean();

        // The record has been refreshed in history
        String historyRecord = "Refresh operation" + portfolioName + " at " + now;

        // Simulate whether there is any new data
        if (hasNewData) {
            // logic for actually refreshing the data can be added
            lastRefreshTime = now;
            outputBoundary.prepareSuccessView("Has been updated", true);
        } else {
            outputBoundary.prepareSuccessView("Latest data has been provided", false);
        }
    }
}


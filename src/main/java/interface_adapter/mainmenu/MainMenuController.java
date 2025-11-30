package interface_adapter.mainmenu;

import use_case.mainmenu.MainMenuInputBoundary;

public class MainMenuController {

    private final MainMenuInputBoundary mainMenuInteractor;

    public MainMenuController(MainMenuInputBoundary mainMenuInteractor) {
        this.mainMenuInteractor = mainMenuInteractor;
    }

    public void executeLogout() {
        mainMenuInteractor.executeLogout();
    }

    public void switchToPortfolioView() {
        mainMenuInteractor.switchToPortfolioView();
    }

    public void switchToSearchView() {
        mainMenuInteractor.switchToSearchView();
    }

    /**
     * Navigates to the Add Stock View for a specific portfolio.
     * @param portfolioName The name of the portfolio to manage.
     * @param username The current user's username.
     */
    public void goToAddStock(String portfolioName, String username) {
        mainMenuInteractor.switchToAddStockView(portfolioName, username);
    }
}
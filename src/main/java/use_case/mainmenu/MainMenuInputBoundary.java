package use_case.mainmenu;

/**
 * Input Boundary for the Main Menu Use Case.
 * Defines the actions a user can take from the main menu.
 */
public interface MainMenuInputBoundary {

    /**
     * Logs the user out and returns to the login screen.
     */
    void executeLogout();

    /**
     * Navigates the user to the Portfolio view.
     */
    void switchToPortfolioView();

    /**
     * Navigates the user to the Search/Analysis view.
     */
    void switchToSearchView();

    /**
     * Navigates the user to the Add Stock View for a specific portfolio.
     * @param portfolioName The name of the selected portfolio.
     * @param username The current user's username.
     */
    void switchToAddStockView(String portfolioName, String username);
}
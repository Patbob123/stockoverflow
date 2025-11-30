package use_case.mainmenu;

/**
 * Output Boundary for the Main Menu Use Case.
 * Defines the methods to update the view model or change the active view.
 */
public interface MainMenuOutputBoundary {

    /**
     * Prepares and presents the Login view (after logout).
     */
    void prepareLoginView();

    /**
     * Prepares and presents the Portfolio view.
     */
    void preparePortfolioView();

    /**
     * Prepares and presents the Search/Stock Analysis view.
     */
    void prepareSearchView();

    /**
     * Prepares and presents the Add Stock view for a specific portfolio.
     * @param portfolioName The name of the portfolio being managed.
     * @param username The current user.
     */
    void prepareAddStockView(String portfolioName, String username);
}
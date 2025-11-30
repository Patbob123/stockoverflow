package use_case.mainmenu;

/**
 * Interactor for the Main Menu.
 * Handles the business logic for navigating between different sections of the app.
 */
public class MainMenuInteractor implements MainMenuInputBoundary {

    private final MainMenuOutputBoundary mainMenuOutputBoundary;

    public MainMenuInteractor(MainMenuOutputBoundary mainMenuOutputBoundary) {
        this.mainMenuOutputBoundary = mainMenuOutputBoundary;
    }

    @Override
    public void executeLogout() {
        // Here you might want to clear any session data or current user state.
        mainMenuOutputBoundary.prepareLoginView();
    }

    @Override
    public void switchToPortfolioView() {
        mainMenuOutputBoundary.preparePortfolioView();
    }

    @Override
    public void switchToSearchView() {
        mainMenuOutputBoundary.prepareSearchView();
    }

    @Override
    public void switchToAddStockView(String portfolioName, String username) {
        // Logic: Prepare the Add Stock View with the context of the selected portfolio
        mainMenuOutputBoundary.prepareAddStockView(portfolioName, username);
    }
}
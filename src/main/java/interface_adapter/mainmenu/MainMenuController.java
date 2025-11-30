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

    public void switchToSingleStockView() {
        mainMenuInteractor.switchToSingleStockView();
    }

    public void goToAddStock(String portfolioName, String username) {
        mainMenuInteractor.switchToAddStockView(portfolioName, username);
    }
}
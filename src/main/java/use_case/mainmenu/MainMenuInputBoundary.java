package use_case.mainmenu;

public interface MainMenuInputBoundary {
    void executeLogout();
    void switchToPortfolioView();
    void switchToSingleStockView();
    void switchToAddStockView(String portfolioName, String username);
}
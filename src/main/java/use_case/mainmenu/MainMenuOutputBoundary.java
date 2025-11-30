package use_case.mainmenu;

public interface MainMenuOutputBoundary {
    void prepareLoginView();
    void preparePortfolioView();
    void prepareSingleStockView();
    void prepareAddStockView(String portfolioName, String username);
}
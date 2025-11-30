package use_case.mainmenu;

public class MainMenuInteractor implements MainMenuInputBoundary {

    private final MainMenuOutputBoundary outputBoundary;

    public MainMenuInteractor(MainMenuOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void executeLogout() {
        outputBoundary.prepareLoginView();
    }

    @Override
    public void switchToPortfolioView() {
        outputBoundary.preparePortfolioView();
    }

    @Override
    public void switchToSingleStockView() {
        outputBoundary.prepareSingleStockView();
    }

    @Override
    public void switchToAddStockView(String portfolioName, String username) {
        outputBoundary.prepareAddStockView(portfolioName, username);
    }
}
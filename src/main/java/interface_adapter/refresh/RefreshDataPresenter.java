package interface_adapter.refresh;

import use_case.refresh.RefreshDataOutputBoundary;
import view.PortfolioMenuView;

public class RefreshDataPresenter implements RefreshDataOutputBoundary {
    private final PortfolioMenuView portfolioMenuView;

    public RefreshDataPresenter(PortfolioMenuView portfolioMenuView) {
        this.portfolioMenuView = portfolioMenuView;
    }

    @Override
    public void prepareSuccessView(String message, boolean hasNewData) {
        portfolioMenuView.showRefreshMessage(message);
        if (hasNewData) {
            portfolioMenuView.updatePortfolioData();
        }
    }

    @Override
    public void prepareFailureView(String errorMessage) {
        portfolioMenuView.showRefreshMessage(errorMessage);
    }
}

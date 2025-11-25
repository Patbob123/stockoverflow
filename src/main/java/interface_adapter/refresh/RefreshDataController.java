package interface_adapter.refresh;

import use_case.refresh.RefreshDataInputBoundary;

public class RefreshDataController {
    private final RefreshDataInputBoundary refreshDataUseCaseInteractor;

    public RefreshDataController(RefreshDataInputBoundary refreshDataUseCaseInteractor) {
        this.refreshDataUseCaseInteractor = refreshDataUseCaseInteractor;
    }

    public void execute(String portfolioName) {
        refreshDataUseCaseInteractor.execute(portfolioName);
    }
}

package use_case.change_view;

public class ChangeViewInteractor implements ChangeViewInputBoundary {

    private final ChangeViewOutputBoundary changeScreenPresenter;

    public ChangeViewInteractor(ChangeViewOutputBoundary changeScreenPresenter) {
        this.changeScreenPresenter = changeScreenPresenter;
    }

    @Override
    public void changeToMainMenu() {
        final ChangeViewOutputData outputData = new ChangeViewOutputData("MainMenu");
        changeScreenPresenter.prepareView(outputData);
    }

    @Override
    public void changeToCreatePortfolio() {
        final ChangeViewOutputData outputData = new ChangeViewOutputData("CreatePortfolioMenu");
        changeScreenPresenter.prepareView(outputData);
    }

    @Override
    public void changeToPortfolio() {
        final ChangeViewOutputData outputData = new ChangeViewOutputData("PortfolioMenu");
        changeScreenPresenter.prepareView(outputData);
    }

    @Override
    public void changeToSimulation() {
        final ChangeViewOutputData outputData = new ChangeViewOutputData("SimulationView");
        changeScreenPresenter.prepareView(outputData);
    }
}

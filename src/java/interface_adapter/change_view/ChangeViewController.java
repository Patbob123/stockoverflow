package interface_adapter.change_view;

import use_case.change_view.ChangeViewInputBoundary;

public class ChangeViewController {

    private final ChangeViewInputBoundary changeViewInteractor;

    public ChangeViewController(ChangeViewInputBoundary changeViewInteractor) {
        this.changeViewInteractor = changeViewInteractor;
    }

    public void changeView(String viewName) {
        System.out.println(viewName);
        switch (viewName) {
            case "MainMenu":
                changeViewInteractor.changeToMainMenu();
                break;
            case "CreatePortfolioMenu":
                changeViewInteractor.changeToCreatePortfolio();
                break;
            case "PortfolioMenu":
                changeViewInteractor.changeToPortfolio();
                break;
            case "SimulationView":
                changeViewInteractor.changeToSimulation();
                break;
            default:
                System.out.println(viewName + " view dont exist");
                break;
        }

    }

}

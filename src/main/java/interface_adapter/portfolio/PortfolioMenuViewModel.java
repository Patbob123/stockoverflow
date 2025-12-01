package interface_adapter.portfolio;

import interface_adapter.ViewModel;
import view.PortfolioMenuView;

public class PortfolioMenuViewModel extends ViewModel<PortfolioMenuState> {

//    public String title_lable = this.getState().getPortfolio() == null ?
//            "Portfolio" : this.getState().getPortfolio().getName();

    public static final String ADD_BUTTON_LABEL = "Add Stock";
    public static final String REMOVE_BUTTON_LABEL = "Remove Selected Stocks";
    public static final String SIMULATION_BUTTON_LABEL = "Simulate";
    public static final String SELECT_ALL_BUTTON_LABEL = "Select All";
    public static final String CLEAR_SELECTION_BUTTON_LABEL = "Clear Selection";
    public static final String SAVE_PORTFOLIO_JSON_BUTTON_LABEL = "Save Portfolio as JSON";
    public static final String SAVE_PORTFOLIO_DATABASE_BUTTON_LABEL = "Save Portfolio in Database";
    public static final String EXIT_BUTTON_LABEL = "Back to Main Menu";
    public static final String CHANGE_NAME_LABEL = "Change Name";

    public PortfolioMenuViewModel() {
        super(PortfolioMenuView.VIEW_NAME);
        setState(new PortfolioMenuState());
    }
    public PortfolioMenuViewModel(PortfolioMenuState portfolioMenuState) {
        super(PortfolioMenuView.VIEW_NAME);
        setState(portfolioMenuState);
    }

}

package interface_adapter.mainmenu;

import interface_adapter.ViewModel;
import view.MainMenuView;

public class MainMenuViewModel extends ViewModel<MainMenuState> {

    public static final String TITLE_LABEL = "Stockoverflow Dashboard";

    public static final int PADDING = 15;

    public static final String STOCK_BUTTON_LABEL = "Analyze Single Stock";
    public static final String PORTFOLIO_BUTTON_LABEL = "Analyze Portfolios";
    public static final String CREATE_PORTFOLIO_BUTTON_LABEL = "Create/Import Portfolio";
    public static final String HISTORY_BUTTON_LABEL = "History";

    public static final String EXIT_BUTTON_LABEL = "QUIT";

    public MainMenuViewModel() {
        super(MainMenuView.VIEW_NAME);
        setState(new MainMenuState());
    }
}

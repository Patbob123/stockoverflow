package interface_adapter.create_portfolio;

import interface_adapter.ViewModel;

public class CreatePortfolioViewModel extends ViewModel<CreatePortfolioState> {

    public static final String TITLE_LABEL = "Import/Export Portfolio";
    public static final String IMPORT_TITLE_LABEL = "Import";
    public static final String EXPORT_TITLE_LABEL = "Export";

    public static final String CURRENT_SESSION_BUTTON_LABEL = "Current Session";
    public static final String EXPORT_PORTFOLIO_BUTTON_LABEL = "Select Portfolio";
    public static final String SELECT_SIMDATA_BUTTON_LABEL = "Select Sim Data";
    public static final String IMPORT_PORTFOLIO_BUTTON_LABEL = "Select Portfolio";
    public static final String BACK_BUTTON_LABEL = "<-";

    public static final int EXPORT_VERTICAL_STRUT = 10;

    public CreatePortfolioViewModel() {
        super("create portfolio");
        setState(new CreatePortfolioState());
    }
}

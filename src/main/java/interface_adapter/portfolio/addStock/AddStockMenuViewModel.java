package interface_adapter.portfolio.addStock;

import entities.Portfolio.Portfolio;
import interface_adapter.ViewModel;
import view.AddStockMenuView;

public class AddStockMenuViewModel extends ViewModel<AddStockMenuState> {

    public static final String ADDSTOCK_BUTTON_LABEL = "Add Stock";
    public static final String BACK_TO_PORTFOLIO = "Back to portfolio";

    public static final String TICKER_NAME_LABEL = "ticker name:";
    public static final String TICKER_AMOUNT_LABEL = "ticker amount:";

    public AddStockMenuViewModel() {
        super(AddStockMenuView.VIEW_NAME);
        setState(new AddStockMenuState());
    }
}

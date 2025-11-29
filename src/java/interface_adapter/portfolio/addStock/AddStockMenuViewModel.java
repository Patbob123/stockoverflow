package interface_adapter.portfolio.addStock;

import interface_adapter.ViewModel;

public class AddStockMenuViewModel extends ViewModel<AddStockMenuState> {
    public AddStockMenuViewModel(Portfolio portfolio) {
        super("addStock");
        setState(new AddStockMenuState());
    }
}

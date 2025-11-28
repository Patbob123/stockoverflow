package interface_adapter.portfolio.addStock;

import entities.Portfolio.Portfolio;
import interface_adapter.ViewModel;
import view.AddStockMenuView;

public class AddStockMenuViewModel extends ViewModel<AddStockMenuState> {
    public AddStockMenuViewModel(Portfolio portfolio) {
        super(AddStockMenuView.VIEW_NAME);
        setState(new AddStockMenuState());
    }
}

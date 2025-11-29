package interface_adapter.add_stock;

import interface_adapter.ViewModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class AddStockViewModel extends ViewModel {
    public static final String TITLE_LABEL = "Add Stocks to Portfolio";
    public static final String SEARCH_LABEL = "Search Ticker:";
    public static final String ADD_SELECTED_LABEL = "Add Selected Stocks";
    public static final String BACK_LABEL = "Back to Portfolios";

    private AddStockState state = new AddStockState();

    public AddStockViewModel() {
        super("add stock");
    }

    public void setState(AddStockState state) { this.state = state; }
    public AddStockState getState() { return state; }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}

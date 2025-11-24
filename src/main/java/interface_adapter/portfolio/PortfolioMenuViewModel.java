package interface_adapter.portfolio;

import interface_adapter.ViewModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PortfolioMenuViewModel extends ViewModel<PortfolioMenuState> {

    public static final String ADD_BUTTON_LABEL = "Add Stock";
    public static final String REMOVE_BUTTON_LABEL = "Remove Selected Stocks";
    public static final String SIMULATION_BUTTON_LABEL = "Simulate";
    public static final String COMPARE_BUTTON_LABEL = "Compare";
    public static final String SELECT_ALL_BUTTON_LABEL = "Select All";
    public static final String CLEAR_SELECTION_BUTTON_LABEL = "Clear Selection";
    public static final String SAVE_PORTFOLIO_BUTTON_LABEL = "Save Portfolio";
    public static final String EXIT_BUTTON_LABEL = "Back to Main Menu";
    public static final String ANALYZE_BUTTON_LABEL = "Analyze Selected";

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public PortfolioMenuViewModel() {
        super("portfolio"); // View name should probably match "PortfolioMenu" used in ViewManager
        setState(new PortfolioMenuState());
    }

    public void firePropertyChange() {
        support.firePropertyChange("state", null, this.getState());
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}

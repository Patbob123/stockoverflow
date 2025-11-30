package interface_adapter.mainmenu;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MainMenuViewModel extends ViewModel {

    public static final String TITLE_LABEL = "Main Menu";
    public static final String PORTFOLIO_BUTTON_LABEL = "My Portfolios";
    public static final String SINGLE_STOCK_BUTTON_LABEL = "Single Stock Analysis";
    public static final String LOGOUT_BUTTON_LABEL = "Log out";
    public static final String LOGGED_IN_USER_LABEL = "Current User: ";

    private MainMenuState state = new MainMenuState();

    public MainMenuViewModel() {
        super("main menu");
    }

    public void setState(MainMenuState state) {
        this.state = state;
    }

    public MainMenuState getState() {
        return state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
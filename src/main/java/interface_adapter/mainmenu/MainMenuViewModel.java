package interface_adapter.mainmenu;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MainMenuViewModel extends ViewModel {

    public final String TITLE_LABEL = "interface_adapter.mainmenu.Main Menu";
    public final String PORTFOLIO_BUTTON_LABEL = "My Portfolios";
    public final String SEARCH_BUTTON_LABEL = "Search Stock";
    public final String LOGOUT_BUTTON_LABEL = "Log out";
    public final String LOGGED_IN_USER_LABEL = "Current User: ";

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
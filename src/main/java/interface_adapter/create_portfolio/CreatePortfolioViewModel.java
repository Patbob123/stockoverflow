package interface_adapter.create_portfolio;

import interface_adapter.ViewModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CreatePortfolioViewModel extends ViewModel {
    public static final String TITLE_LABEL = "My Portfolios";
    public static final String CREATE_BUTTON_LABEL = "Create Portfolio";
    public static final String INPUT_LABEL = "New Portfolio Name:";
    public static final String BACK_LABEL = "Back to Main Menu";

    private CreatePortfolioState state = new CreatePortfolioState();

    public CreatePortfolioViewModel() {
        super("create portfolio");
    }

    public void setState(CreatePortfolioState state) { this.state = state; }
    public CreatePortfolioState getState() { return state; }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}

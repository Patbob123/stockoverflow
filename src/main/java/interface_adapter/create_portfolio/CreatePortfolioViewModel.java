package interface_adapter.create_portfolio;

import interface_adapter.ViewModel;
import interface_adapter.import_export.ImportExportState;
import view.CreatePortfolioMenuView;
import view.ImportExportView;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CreatePortfolioViewModel extends ViewModel<CreatePortfolioState> {
    public static final String TITLE_LABEL = "My Portfolios";
    public static final String CREATE_BUTTON_LABEL = "Create Portfolio";
    public static final String INPUT_LABEL = "New Portfolio Name:";
    public static final String BACK_LABEL = "Back to Main Menu";

    private CreatePortfolioState state = new CreatePortfolioState();

    public CreatePortfolioViewModel() {
        super(CreatePortfolioMenuView.VIEW_NAME);
        setState(new CreatePortfolioState());
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

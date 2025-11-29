package interface_adapter.show_graph;

import interface_adapter.ViewModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ShowGraphViewModel extends ViewModel {
    public static final String TITLE_LABEL = "Stock Price Graph";

    private ShowGraphState state = new ShowGraphState();

    public ShowGraphViewModel() {
        super("show graph");
    }

    public void setState(ShowGraphState state) {
        this.state = state;
    }

    public ShowGraphState getState() {
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
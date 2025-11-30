package interface_adapter.monte_carlo;

import interface_adapter.ViewModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MonteCarloViewModel extends ViewModel {
    private MonteCarloState state = new MonteCarloState();

    public MonteCarloViewModel() {
        super("monte carlo");
    }

    public void setState(MonteCarloState state) { this.state = state; }
    public MonteCarloState getState() { return state; }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    public void firePropertyChanged() { support.firePropertyChange("state", null, this.state); }
    public void addPropertyChangeListener(PropertyChangeListener listener) { support.addPropertyChangeListener(listener); }
}

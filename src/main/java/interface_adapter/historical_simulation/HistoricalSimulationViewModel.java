package interface_adapter.historical_simulation;

import interface_adapter.ViewModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class HistoricalSimulationViewModel extends ViewModel<HistoricalSimulationState> {
    public static final String VIEW_NAME = "historical simulation";
    private HistoricalSimulationState state = new HistoricalSimulationState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public HistoricalSimulationViewModel() {
        super(VIEW_NAME);
    }

    public HistoricalSimulationState getState() { return state; }
    public void setState(HistoricalSimulationState state) { this.state = state; }

    @Override
    public void firePropertyChange() {
        support.firePropertyChange("state", null, this.state);
    }
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}

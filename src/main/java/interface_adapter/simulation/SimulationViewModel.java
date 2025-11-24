package interface_adapter.simulation;

import interface_adapter.ViewModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SimulationViewModel extends ViewModel {
    public static final String TITLE_LABEL = "Simulation";
    public static final String TICKER_LABEL = "Stock Ticker:";
    public static final String SIMULATE_BUTTON_LABEL = "Simulate";
    public static final String BACK_BUTTON_LABEL = "Back";

    private SimulationState state = new SimulationState();

    public SimulationViewModel() {
        super("simulation");
    }

    public void setState(SimulationState state) {
        this.state = state;
    }

    public SimulationState getState() {
        return state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChange() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}

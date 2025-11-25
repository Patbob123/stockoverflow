package interface_adapter.user.create;

import lombok.Getter;
import lombok.Setter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CreateAccountViewModel {
    @Setter
    @Getter
    private String message;
    @Setter
    @Getter
    private boolean success;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChange() {
        support.firePropertyChange("state", null, this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}

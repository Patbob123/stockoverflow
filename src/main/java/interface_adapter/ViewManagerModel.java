package interface_adapter;

import lombok.Getter;
import lombok.Setter;
import view.ViewManager;

/**
 * Model for the View Manager. Its state is the name of the View which
 * is currently active. An initial state of "" is used.
 */
public class ViewManagerModel extends ViewModel<String> {

    @Getter
    @Setter
    private ViewModel<?> formativeViewModel;

    public static final String GET_VIEW_MODEL_NAME = "getViewModel";

    public ViewManagerModel() {
        super("view manager");
        this.setState("");
    }

    public void setActiveView(String viewName) {
        this.setState(viewName);
        this.firePropertyChange();
    }

    public ViewModel<?> getViewModel(String viewName) {
        this.setState(viewName);
        this.firePropertyChange(GET_VIEW_MODEL_NAME);
        return this.formativeViewModel;
    }

}

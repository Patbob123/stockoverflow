package interface_adapter.change_view;

import interface_adapter.AbsController;
import interface_adapter.ViewModel;
import use_case.change_view.ChangeViewInputBoundary;

/**
 * Controller for switching views.
 */
public class ChangeViewController extends AbsController {

    private final ChangeViewInputBoundary changeViewInteractor;

    /**
     * Constructor for ChangeViewController
     */
    public ChangeViewController(ChangeViewInputBoundary changeViewInteractor) {
        this.changeViewInteractor = changeViewInteractor;
    }

    /**
     * Switches to the given view
     */
    public void changeView(String viewName) {
        System.out.println(viewName);
        changeViewInteractor.execute(viewName);
    }

    /**
     * Goes back to the previous view
     */
    public void backView() {
        changeViewInteractor.execute("");
    }

    /**
     * Gets the ViewModel for the given view
     */
    public ViewModel<?> getViewModel(String viewName) {
        return this.changeViewInteractor.getViewModel(viewName);
    }


}

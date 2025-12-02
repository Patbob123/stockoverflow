package use_case.change_view;

import interface_adapter.ViewModel;
import use_case.InputBoundary;

/**
 * Input boundary for switching views in STOCKOVERFLOW
 * Allows for change screens, go back, and get view model
 */
public interface ChangeViewInputBoundary extends InputBoundary {

    /**
     * Goes back to a previous view
     */
    void goBack();

    /**
     * Switches to the view with the name
     */
    void changeTo(String viewName);

    /**
     * Returns the ViewModel for the given view name
     */
    ViewModel<?> getViewModel(String viewName);
}
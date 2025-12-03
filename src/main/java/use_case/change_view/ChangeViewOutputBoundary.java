package use_case.change_view;

import interface_adapter.ViewModel;
import use_case.OutputBoundary;

/**
 * Output boundary for changing views.
 */
public interface ChangeViewOutputBoundary extends OutputBoundary {

    /**
     * Tells the presenter to switch to the view described by the output data.
     */
    void prepareView(ChangeViewOutputData outputData);

    /**
     * Returns the ViewModel for the given view name
     */
    ViewModel<?> getViewModel(String viewName);
}

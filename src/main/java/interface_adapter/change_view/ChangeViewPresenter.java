package interface_adapter.change_view;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;
import use_case.change_view.ChangeViewOutputBoundary;
import use_case.change_view.ChangeViewOutputData;

/**
 * Presenter for switching views
 */
public class ChangeViewPresenter implements ChangeViewOutputBoundary {

    private final ViewManagerModel viewManagerModel;

    /**
     * Constructor for ChangeViewPresenter
     */
    public ChangeViewPresenter(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Sets the active view
     */
    @Override
    public void prepareView(ChangeViewOutputData data) {
        viewManagerModel.setActiveView(data.getViewName());
    }

    /**
     * Returns the ViewModel for a given view name
     */
    @Override
    public ViewModel<?> getViewModel(String viewName) {
        return this.viewManagerModel.getViewModel(viewName);
    }
}

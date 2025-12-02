package use_case.change_view;

import interface_adapter.ViewModel;
import interface_adapter.change_view.ChangeViewState;

/**
 * Interactor that handles changing views
 */
public class ChangeViewInteractor implements ChangeViewInputBoundary {

    private final ChangeViewState changeViewState;
    private final ChangeViewOutputBoundary changeScreenPresenter;

    /**
     * Constructor for ChangeViewInteractor
     */
    public ChangeViewInteractor(ChangeViewOutputBoundary changeScreenPresenter, ChangeViewState changeViewState) {
        this.changeViewState = changeViewState;
        this.changeScreenPresenter = changeScreenPresenter;
    }

    /**
     * Goes back to a previous view
     */
    @Override
    public void goBack() {
        if (changeViewState.canGoBack()) {
            final String previousView = changeViewState.popView();
            final ChangeViewOutputData outputData = new ChangeViewOutputData(previousView);
            changeScreenPresenter.prepareView(outputData);
        }
    }

    /**
     * Switches to the view with the name
     */
    @Override
    public void changeTo(String viewName) {
        changeViewState.pushView(viewName);
        final ChangeViewOutputData outputData = new ChangeViewOutputData(viewName);
        changeScreenPresenter.prepareView(outputData);
    }

    /**
     * Returns the ViewModel for the given view name
     */
    @Override
    public ViewModel<?> getViewModel(String viewName) {
        return changeScreenPresenter.getViewModel(viewName);
    }
}
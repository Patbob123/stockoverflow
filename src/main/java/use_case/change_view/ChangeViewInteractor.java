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
     * Switches to the view with the name or goes back
     */
    @Override
    public void execute(String viewName) {
        if (viewName.isEmpty()) {
            if (changeViewState.canGoBack()) {
                String prev = changeViewState.popView();
                changeScreenPresenter.prepareView(new ChangeViewOutputData(prev));
            }
        } else {
            changeViewState.pushView(viewName);
            changeScreenPresenter.prepareView(new ChangeViewOutputData(viewName));
        }
    }
    /**
     * Returns the ViewModel for the given view name
     */
    @Override
    public ViewModel<?> getViewModel(String viewName) {
        return changeScreenPresenter.getViewModel(viewName);
    }
}
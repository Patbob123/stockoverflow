package use_case.change_view;

import interface_adapter.ViewModel;
import interface_adapter.change_view.ChangeViewState;

public class ChangeViewInteractor implements ChangeViewInputBoundary {

    private final ChangeViewOutputBoundary changeScreenPresenter;
    private final ChangeViewState changeViewState;

    public ChangeViewInteractor(ChangeViewOutputBoundary changeScreenPresenter, ChangeViewState changeViewState) {
        this.changeScreenPresenter = changeScreenPresenter;
        this.changeViewState = changeViewState;
    }

    @Override
    public void changeTo(String viewName) {
        changeViewState.pushView(viewName);
        final ChangeViewOutputData outputData = new ChangeViewOutputData(viewName);
        changeScreenPresenter.prepareView(outputData);
    }

    @Override
    public void goBack() {
        if (changeViewState.canGoBack()) {
            final String previousView = changeViewState.popView();
            final ChangeViewOutputData outputData = new ChangeViewOutputData(previousView);
            changeScreenPresenter.prepareView(outputData);
        }
    }

    @Override
    public ViewModel<?> getViewModel(String viewName) {
        return changeScreenPresenter.getViewModel(viewName);
    }
}
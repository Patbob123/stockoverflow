package use_case.change_view;

public class ChangeViewInteractor implements ChangeViewInputBoundary {

    private final ChangeViewOutputBoundary changeScreenPresenter;

    public ChangeViewInteractor(ChangeViewOutputBoundary changeScreenPresenter) {
        this.changeScreenPresenter = changeScreenPresenter;
    }

    @Override
    public void changeTo(String viewName) {
        final ChangeViewOutputData outputData = new ChangeViewOutputData(viewName);
        changeScreenPresenter.prepareView(outputData);
    }

}

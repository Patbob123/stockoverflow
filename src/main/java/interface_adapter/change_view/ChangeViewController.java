package interface_adapter.change_view;

import use_case.change_view.ChangeViewInputBoundary;

public class ChangeViewController {

    private final ChangeViewInputBoundary changeViewInteractor;

    public ChangeViewController(ChangeViewInputBoundary changeViewInteractor) {
        this.changeViewInteractor = changeViewInteractor;
    }

    public void changeView(String viewName) {
        System.out.println(viewName);
        changeViewInteractor.changeTo(viewName);
    }

    public void backView() {
        changeViewInteractor.goBack();
    }

}

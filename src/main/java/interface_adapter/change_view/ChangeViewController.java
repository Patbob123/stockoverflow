package interface_adapter.change_view;

import interface_adapter.AbsController;
import interface_adapter.ViewModel;
import use_case.change_view.ChangeViewInputBoundary;

public class ChangeViewController extends AbsController {

    private final ChangeViewInputBoundary changeViewInteractor;

    public ChangeViewController(ChangeViewInputBoundary changeViewInteractor) {
        this.changeViewInteractor = changeViewInteractor;
    }

    public void changeView(String viewName) {
        System.out.println(viewName);
        changeViewInteractor.changeTo(viewName);
    }

    public ViewModel<?> getViewModel(String viewName) {
        return this.changeViewInteractor.getViewModel(viewName);
    }

    public void backView() {
        changeViewInteractor.goBack();
    }

}

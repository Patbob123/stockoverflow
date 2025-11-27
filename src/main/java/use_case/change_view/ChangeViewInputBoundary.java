package use_case.change_view;

import interface_adapter.ViewModel;

public interface ChangeViewInputBoundary {
    void changeTo(String viewName);
    void goBack();
    ViewModel<?> getViewModel(String viewName);
}

package use_case.change_view;

import interface_adapter.ViewModel;
import use_case.InputBoundary;

public interface ChangeViewInputBoundary extends InputBoundary {
    void changeTo(String viewName);
    void goBack();
    ViewModel<?> getViewModel(String viewName);
}

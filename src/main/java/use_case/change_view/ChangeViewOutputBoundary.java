package use_case.change_view;

import interface_adapter.ViewModel;

public interface ChangeViewOutputBoundary {
    void prepareView(ChangeViewOutputData outputData);
    ViewModel<?> getViewModel(String viewName);
}

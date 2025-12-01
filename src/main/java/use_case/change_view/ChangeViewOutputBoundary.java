package use_case.change_view;

import interface_adapter.ViewModel;
import use_case.OutputBoundary;

public interface ChangeViewOutputBoundary extends OutputBoundary {
    void prepareView(ChangeViewOutputData outputData);
    ViewModel<?> getViewModel(String viewName);
}

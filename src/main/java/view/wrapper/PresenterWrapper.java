package view.wrapper;

import interface_adapter.ViewModel;
import use_case.OutputBoundary;

public interface PresenterWrapper<O extends OutputBoundary, T extends ViewModel<?>> {
    O create(T viewModel);
}

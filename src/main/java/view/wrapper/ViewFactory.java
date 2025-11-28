package view.wrapper;

import interface_adapter.ViewModel;
import view.PaddedView;

public interface ViewFactory<T extends ViewModel<?>, P extends PaddedView<T, ?>> {
    P createView(T viewModel);
}

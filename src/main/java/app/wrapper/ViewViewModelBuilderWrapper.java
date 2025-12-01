package app.wrapper;

import app.AppBuilder;
import interface_adapter.ViewModel;
import lombok.Getter;
import lombok.Setter;
import view.PaddedView;
import view.ViewManager;

import javax.swing.*;

@Getter
@Setter
public class ViewViewModelBuilderWrapper<T extends ViewModel<?>, P extends PaddedView<T, ?>> {
    private T viewModel;
    private P view;

    public ViewViewModelBuilderWrapper(T viewModel, DirectlyCreateFactory<P, T> factory) {
        this.viewModel = viewModel;
        this.view = factory.create(viewModel);
    }

    public AppBuilder addView(AppBuilder builder, JPanel cardPanel, ViewManager viewManager) {
        cardPanel.add(view, viewModel.getViewName());
        viewManager.addView(view.getViewName(), view);
        return builder;
    }
}

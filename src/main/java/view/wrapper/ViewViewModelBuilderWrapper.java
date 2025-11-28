package view.wrapper;

import app.MainMenuBuilder;
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

    public ViewViewModelBuilderWrapper(T viewModel, ViewFactory<T, P> viewFactory) {
        this.viewModel = viewModel;
        this.view = viewFactory.createView(viewModel);
    }

    public MainMenuBuilder addView(MainMenuBuilder builder, JPanel cardPanel, ViewManager viewManager) {
        cardPanel.add(view, viewModel.getViewName());
        viewManager.addView(view.getViewName(), view);
        return builder;
    }
}

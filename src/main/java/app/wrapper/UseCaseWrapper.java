package app.wrapper;

import app.MainMenuBuilder;
import interface_adapter.AbsController;
import interface_adapter.ViewModel;
import lombok.AllArgsConstructor;
import use_case.InputBoundary;
import use_case.OutputBoundary;
import view.PaddedView;
import view.ViewManager;

@AllArgsConstructor
public class UseCaseWrapper<I extends InputBoundary,
        O extends OutputBoundary,
        C extends AbsController,
        T extends ViewModel<?>,
        P extends PaddedView<T, C>> {

    private final ViewManager viewManager;
    private final DirectlyCreateFactory<O, T> presenterWrapper;
    private final DirectlyCreateFactory<I, O> interactorWrapper;
    private final DirectlyCreateFactory<C, I> controllerWrapper;
    private final String viewName;

    public MainMenuBuilder build(MainMenuBuilder mainMenuBuilder) {
        final P menuView = (P) viewManager.getViews().get(viewName);
        final O outputBoundary = presenterWrapper.create(menuView.getViewModel());
        final I inputBoundary = interactorWrapper.create(outputBoundary);
        final C controller = controllerWrapper.create(inputBoundary);
        menuView.setController(controller);
        return mainMenuBuilder;
    }
}

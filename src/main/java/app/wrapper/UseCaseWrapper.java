package app.wrapper;

import app.MainMenuBuilder;
import com.sun.tools.javac.Main;
import interface_adapter.AbsController;
import interface_adapter.ViewModel;
import lombok.AllArgsConstructor;
import use_case.InputBoundary;
import use_case.OutputBoundary;
import view.PaddedView;
import view.ViewManager;

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

    private O presenter;

    public UseCaseWrapper(ViewManager viewManager, DirectlyCreateFactory<O, T> presenterWrapper, DirectlyCreateFactory<I, O> interactorWrapper, DirectlyCreateFactory<C, I> controllerWrapper, String viewName) {
        this.viewManager = viewManager;
        this.presenterWrapper = presenterWrapper;

        this.interactorWrapper = interactorWrapper;
        this.controllerWrapper = controllerWrapper;
        this.viewName = viewName;
    }

    public O makePresenter(P menuView) {
        this.presenter = presenterWrapper.create(menuView.getViewModel());
        return presenter;
    }

    public I getInteractor(P menuView) {
        final O outputBoundary;
        if (this.presenter == null) {
            outputBoundary = makePresenter(menuView);
        }
        else {
            outputBoundary = this.presenter;
        }

        final I inputBoundary = interactorWrapper.create(outputBoundary);
        return inputBoundary;
    };

    public MainMenuBuilder build(MainMenuBuilder mainMenuBuilder) {
        final P menuView = (P) viewManager.getViews().get(viewName);
        final I inputBoundary = this.getInteractor(menuView);
        final C controller = controllerWrapper.create(inputBoundary);
        menuView.setController(controller);
        return mainMenuBuilder;
    }
}

package view.wrapper;

import app.MainMenuBuilder;
import interface_adapter.AbsController;
import interface_adapter.ViewModel;
import interface_adapter.mainmenu.MainMenuController;
import interface_adapter.mainmenu.MainMenuPresenter;
import use_case.InputBoundary;
import use_case.OutputBoundary;
import use_case.mainmenu.MainMenuInputBoundary;
import use_case.mainmenu.MainMenuInteractor;
import use_case.mainmenu.MainMenuOutputBoundary;
import view.MainMenuView;
import view.PaddedView;
import view.ViewManager;

public class UseCaseWrapper<I extends InputBoundary,
        O extends OutputBoundary,
        C extends AbsController,
        T extends ViewModel<?>,
        P extends PaddedView<T, C>> {

    public MainMenuBuilder useCaseWrapper(MainMenuBuilder mainMenuBuilder,
                          ViewManager viewManager, DirectlyCreateFactory<O, T> presenterWrapper,
                          DirectlyCreateFactory<I, O> interactorWrapper,
                          DirectlyCreateFactory<C, I> controllerWrapper, String viewName) {
        final P menuView = (P) viewManager.getViews().get(viewName);
        final O outputBoundary = presenterWrapper.create(menuView.getViewModel());
        final I inputBoundary = interactorWrapper.create(outputBoundary);

        final C controller = controllerWrapper.create(inputBoundary);
        menuView.setController(controller);
        return mainMenuBuilder;
    }
}

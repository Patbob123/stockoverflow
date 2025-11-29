package interface_adapter.mainmenu;

import interface_adapter.ViewManagerModel;
import interface_adapter.Login.LoginState;
import interface_adapter.Login.LoginViewModel;
// import use_case.logout.LogoutOutputBoundary;

public class MainMenuPresenter /* implements LogoutOutputBoundary */ {

    private final MainMenuViewModel mainMenuViewModel;
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;

    public MainMenuPresenter(ViewManagerModel viewManagerModel,
                             MainMenuViewModel mainMenuViewModel,
                             LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.mainMenuViewModel = mainMenuViewModel;
        this.loginViewModel = loginViewModel;
    }

    // @Override
    public void prepareSuccessView() {
        MainMenuState mainState = mainMenuViewModel.getState();
        mainState.setUsername("");
        mainMenuViewModel.setState(mainState);
        mainMenuViewModel.firePropertyChanged();

        LoginState loginState = loginViewModel.getState();
        loginState.setPassword("");
        loginViewModel.setState(loginState);
        loginViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    // @Override
    public void prepareFailView(String error) {
        MainMenuState state = mainMenuViewModel.getState();
        state.setErrorMessage(error);
        mainMenuViewModel.firePropertyChanged();
    }
}
package interface_adapter.mainmenu;

import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import use_case.mainmenu.MainMenuOutputBoundary;

public class MainMenuPresenter implements MainMenuOutputBoundary {

    private final MainMenuViewModel mainMenuViewModel;

    public MainMenuPresenter(MainMenuViewModel mainMenuViewModel) {
        this.mainMenuViewModel = mainMenuViewModel;
    }

    @Override
    public void prepareLoginView() {
        MainMenuState mainState = mainMenuViewModel.getState();
        mainState.setUsername("");
        mainMenuViewModel.setState(mainState);
        mainMenuViewModel.firePropertyChange();

        LoginViewModel loginViewModel = mainMenuViewModel.getState().getLoginViewModel();
        LoginState loginState = loginViewModel.getState();
        loginState.setPassword("");
        loginViewModel.setState(loginState);
        loginViewModel.firePropertyChanged();
    }

    @Override
    public void prepareSuccessView(String message) {
        System.out.println("Main menu did something idk what dont ask me");
        mainMenuViewModel.firePropertyChange();
    }
}

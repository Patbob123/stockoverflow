package interface_adapter.Login;

import interface_adapter.ViewManagerModel;
import interface_adapter.mainmenu.MainMenuState;
import interface_adapter.mainmenu.MainMenuViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final MainMenuViewModel mainMenuViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          MainMenuViewModel mainMenuViewModel,
                          LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.mainMenuViewModel = mainMenuViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        MainMenuState mainMenuState = mainMenuViewModel.getState();
        mainMenuState.setUsername(response.getUsername());
        this.mainMenuViewModel.setState(mainMenuState);
        this.mainMenuViewModel.firePropertyChanged();

        this.viewManagerModel.setActiveView(mainMenuViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        LoginState loginState = loginViewModel.getState();
        loginState.setUsernameError(error);
        loginViewModel.firePropertyChanged();
    }
}

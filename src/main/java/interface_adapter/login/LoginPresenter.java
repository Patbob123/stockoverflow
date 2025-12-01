package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.mainmenu.MainMenuState;
import interface_adapter.mainmenu.MainMenuViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final MainMenuViewModel mainMenuViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoginPresenter(MainMenuViewModel mainMenuViewModel, LoginViewModel loginViewModel, ViewManagerModel viewManagerModel) {
        this.mainMenuViewModel = mainMenuViewModel;
        this.loginViewModel = loginViewModel;
        this.viewManagerModel = viewManagerModel;
}

@Override
public void prepareSuccessView(LoginOutputData response) {
        this.mainMenuViewModel.getState().setUsername(response.getUsername());
//    MainMenuState mainMenuState = mainMenuViewModel.getState();
//    mainMenuState.setUsername(response.getUsername());
//    this.mainMenuViewModel.setState(mainMenuState);
    this.mainMenuViewModel.firePropertyChange();

    this.viewManagerModel.setActiveView(mainMenuViewModel.getViewName());
    this.viewManagerModel.firePropertyChange();
}

    @Override
    public void prepareFailView(String error) {
        LoginState loginState = loginViewModel.getState();
        loginState.setUsernameError(error);
        loginViewModel.firePropertyChanged();
    }
}

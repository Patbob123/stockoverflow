package interface_adapter.user.login;

import use_case.user.login.LoginOutputBoundary;
import use_case.user.login.LoginOutputData;
import view.LoginView;
import view.MainMenuView;

public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final LoginView loginView;
    private final MainMenuView mainMenuView;

    public LoginPresenter(LoginViewModel loginViewModel, LoginView loginView, MainMenuView mainMenuView) {
        this.loginViewModel = loginViewModel;
        this.loginView = loginView;
        this.mainMenuView = mainMenuView;
    }

    @Override
    public void prepareSuccessView(LoginOutputData outputData) {
        loginViewModel.setMessage("login successfully");
        loginViewModel.setSuccess(true);
        loginViewModel.firePropertyChange();

        // Switch to the main menu view
        loginView.setVisible(false);
        mainMenuView.setVisible(true);
    }

    @Override
    public void prepareFailureView(String errorMessage) {
        loginViewModel.setMessage(errorMessage);
        loginViewModel.setSuccess(false);
        loginViewModel.firePropertyChange();
    }
}

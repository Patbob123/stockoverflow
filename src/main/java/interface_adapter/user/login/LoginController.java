package interface_adapter.user.login;

import use_case.user.login.LoginInputBoundary;
import use_case.user.login.LoginInputData;

public class LoginController {
    private final LoginInputBoundary loginUseCaseInteractor;

    public LoginController(LoginInputBoundary loginUseCaseInteractor) {
        this.loginUseCaseInteractor = loginUseCaseInteractor;
    }

    public void execute(String username, String password) {
        LoginInputData inputData = new LoginInputData(username, password);
        loginUseCaseInteractor.execute(inputData);
    }
}

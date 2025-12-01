package interface_adapter.login;

import interface_adapter.AbsController;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;

public class LoginController extends AbsController {
    final LoginInputBoundary loginUseCaseInteractor;

    public LoginController(LoginInputBoundary loginUseCaseInteractor) {
        this.loginUseCaseInteractor = loginUseCaseInteractor;
    }

    public boolean execute(String username, String password) {
        LoginInputData loginInputData = new LoginInputData(username, password);
        return loginUseCaseInteractor.execute(loginInputData);
    }
}

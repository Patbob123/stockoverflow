package use_case.login;

import data_access.UserDataAccessInterface;
import entities.User;
import service.SessionManager;
import utils.PasswordUtils;

import java.util.Optional;

public class LoginInteractor implements LoginInputBoundary {

    private final UserDataAccessInterface userDataAccess;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(UserDataAccessInterface userDataAccess, LoginOutputBoundary loginPresenter) {
        this.userDataAccess = userDataAccess;
        this.loginPresenter = loginPresenter;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        // find user
        Optional<User> userOptional = userDataAccess.findByUsername(loginInputData.getUsername());

        if (userOptional.isEmpty()) {
            loginPresenter.prepareFailView("Username not found");
            return;
        }

        User user = userOptional.get();

        // verify code
        if (!PasswordUtils.checkPassword(loginInputData.getPassword(), user.getPasswordHash())) {
            loginPresenter.prepareFailView("Incorrect password");
            return;
        }

        // Login successful. Session created
        SessionManager.getInstance().login(user);

        // success
        LoginOutputData outputData = new LoginOutputData(
                user.getUsername(),
                true,
                "Login successful"
        );
        loginPresenter.prepareSuccessView(outputData);
    }

    @Override
    public void executeGuestLogin() {
        SessionManager.getInstance().loginAsGuest();
        loginPresenter.prepareGuestView();
    }
}
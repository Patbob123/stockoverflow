package use_case.signup;

import data_access.UserDataAccessInterface;
import entities.User;
import service.SessionManager;
import utils.PasswordUtils;

public class SignupInteractor implements SignupInputBoundary {

    private final UserDataAccessInterface userDataAccess;
    private final SignupOutputBoundary signupPresenter;

    public SignupInteractor(UserDataAccessInterface userDataAccess, SignupOutputBoundary signupPresenter) {
        this.userDataAccess = userDataAccess;
        this.signupPresenter = signupPresenter;
    }

    @Override
    public void execute(SignupInputData signupInputData) {
        // user name
        if (signupInputData.getUsername().isEmpty()) {
            signupPresenter.prepareFailView("Username cannot be empty");
            return;
        }

        // mail format
        if (!isValidEmail(signupInputData.getEmail())) {
            signupPresenter.prepareFailView("Invalid email format");
            return;
        }

        // verify
        if (signupInputData.getPassword().length() < 8) {
            signupPresenter.prepareFailView("Password must be at least 8 characters");
            return;
        }

        // check name exist
        if (userDataAccess.existsByUsername(signupInputData.getUsername())) {
            signupPresenter.prepareFailView("Username already exists");
            return;
        }

        // check mail exist
        if (userDataAccess.existsByEmail(signupInputData.getEmail())) {
            signupPresenter.prepareFailView("Email already exists");
            return;
        }

        // Not sure code
        String passwordHash = PasswordUtils.hashPassword(signupInputData.getPassword());

        // create
        User user = new User(
                signupInputData.getUsername(),
                passwordHash,
                signupInputData.getEmail()
        );

        // save
        userDataAccess.save(user);

        // log
        SessionManager.getInstance().login(user);

        // success
        SignupOutputData outputData = new SignupOutputData(
                user.getUsername(),
                true,
                "Registration successful"
        );
        signupPresenter.prepareSuccessView(outputData);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && email.matches(emailRegex);
    }
}
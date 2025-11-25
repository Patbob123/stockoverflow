package use_case.user.login;

import entities.User;
import entities.UserRepository;
import entities.UserSession;

public class LoginInteractor implements LoginInputBoundary {
    private final LoginOutputBoundary outputBoundary;
    private final UserRepository userRepository;
    private final UserSession userSession;

    public LoginInteractor(LoginOutputBoundary outputBoundary, UserRepository userRepository, UserSession userSession) {
        this.outputBoundary = outputBoundary;
        this.userRepository = userRepository;
        this.userSession = userSession;
    }

    @Override
    public void execute(LoginInputData inputData) {
        String username = inputData.getUsername();
        String password = inputData.getPassword();

        // check
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            outputBoundary.prepareFailureView("User name does not exist");
            return;
        }

        // verify
        if (!user.getPassword().equals(password)) {
            outputBoundary.prepareFailureView("Password is incorrect");
            return;
        }

        // Login successful. Update the session
        userSession.setCurrentUser(user);
        outputBoundary.prepareSuccessView(new LoginOutputData(username, true));
    }
}

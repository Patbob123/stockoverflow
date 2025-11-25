package use_case.user.create;

import entities.User;
import entities.UserRepository;

public class CreateAccountInteractor implements CreateAccountInputBoundary {
    private final CreateAccountOutputBoundary outputBoundary;
    private final UserRepository userRepository;

    public CreateAccountInteractor(CreateAccountOutputBoundary outputBoundary, UserRepository userRepository) {
        this.outputBoundary = outputBoundary;
        this.userRepository = userRepository;
    }

    @Override
    public void execute(CreateAccountInputData inputData) {
        String username = inputData.getUsername();
        String password = inputData.getPassword();

        // verify
        if (username == null || username.trim().isEmpty()) {
            outputBoundary.prepareFailureView("Username cannot be empty");
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            outputBoundary.prepareFailureView("Password cannot be empty");
            return;
        }

        // check name
        if (userRepository.usernameExists(username)) {
            outputBoundary.prepareFailureView("Username already exists");
            return;
        }

        // Register
        User user = userRepository.createUser(username, password);
        if (user != null) {
            outputBoundary.prepareSuccessView(new CreateAccountOutputData(username, true));
        } else {
            outputBoundary.prepareFailureView("Try again");
        }
    }
}

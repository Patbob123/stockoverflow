package interface_adapter.signup;

import interface_adapter.AbsController;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInputData;

public class SignupController extends AbsController {
    final SignupInputBoundary userSignupUseCaseInteractor;

    public SignupController(SignupInputBoundary userSignupUseCaseInteractor) {
        this.userSignupUseCaseInteractor = userSignupUseCaseInteractor;
    }

    public void execute(String username, String password, String repeatPassword) {
        SignupInputData signupInputData = new SignupInputData(username, password, repeatPassword);
        userSignupUseCaseInteractor.execute(signupInputData);
    }
}
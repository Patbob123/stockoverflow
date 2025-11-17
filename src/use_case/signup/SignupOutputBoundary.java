package use_case.signup;

public interface SignupOutputBoundary {
    void prepareSuccessView(SignupOutputData signupOutputData);
    void prepareFailView(String errorMessage);
}

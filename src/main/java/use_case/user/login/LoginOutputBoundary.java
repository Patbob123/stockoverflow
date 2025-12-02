package use_case.user.login;

public interface LoginOutputBoundary {
    void prepareSuccessView(LoginOutputData user);
    void prepareFailureView(String errorMessage);
}

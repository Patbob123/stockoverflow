package use_case.user.create;

public interface CreateAccountOutputBoundary {
    void prepareSuccessView(CreateAccountOutputData user);
    void prepareFailureView(String errorMessage);
}

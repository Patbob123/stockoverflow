package use_case.login;

import use_case.OutputBoundary;

public interface LoginOutputBoundary extends OutputBoundary {
    void prepareSuccessView(LoginOutputData user);

    void prepareFailView(String error);
}
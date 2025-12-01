package use_case.login;

import use_case.InputBoundary;

public interface LoginInputBoundary extends InputBoundary {
    void execute(LoginInputData loginInputData);
}

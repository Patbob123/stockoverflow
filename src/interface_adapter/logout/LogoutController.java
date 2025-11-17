package interface_adapter.logout;

import use_case.logout.LogoutInputBoundary;

public class LogoutController {
    private final LogoutInputBoundary logoutUseCaseInteractor;

    public LogoutController(LogoutInputBoundary logoutUseCaseInteractor) {
        this.logoutUseCaseInteractor = logoutUseCaseInteractor;
    }

    public void execute() {
        logoutUseCaseInteractor.execute();
    }
}
package interface_adapter.user.logout;

import use_case.user.logout.LogoutInputBoundary;

public class LogoutController {
    private final LogoutInputBoundary logoutUseCaseInteractor;

    public LogoutController(LogoutInputBoundary logoutUseCaseInteractor) {
        this.logoutUseCaseInteractor = logoutUseCaseInteractor;
    }

    public void execute() {
        logoutUseCaseInteractor.execute();
    }
}

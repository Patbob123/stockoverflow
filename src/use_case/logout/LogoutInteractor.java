package use_case.logout;

import service.SessionManager;

public class LogoutInteractor implements LogoutInputBoundary {

    private final LogoutOutputBoundary logoutPresenter;

    public LogoutInteractor(LogoutOutputBoundary logoutPresenter) {
        this.logoutPresenter = logoutPresenter;
    }

    @Override
    public void execute() {

        SessionManager.getInstance().logout();
        logoutPresenter.prepareSuccessView();
    }
}
package use_case.user.logout;

import entities.UserSession;

public class LogoutInteractor implements LogoutInputBoundary {
    private final LogoutOutputBoundary outputBoundary;
    private final UserSession userSession;

    public LogoutInteractor(LogoutOutputBoundary outputBoundary, UserSession userSession) {
        this.outputBoundary = outputBoundary;
        this.userSession = userSession;
    }

    @Override
    public void execute() {
        // clear
        userSession.clearSession();
        outputBoundary.prepareSuccessView();
    }
}

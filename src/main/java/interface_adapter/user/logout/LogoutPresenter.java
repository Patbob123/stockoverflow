package interface_adapter.user.logout;

import use_case.user.logout.LogoutOutputBoundary;
import view.LoginView;
import view.MainMenuView;

public class LogoutPresenter implements LogoutOutputBoundary {
    private final MainMenuView mainMenuView;
    private final LoginView loginView;

    public LogoutPresenter(MainMenuView mainMenuView, LoginView loginView) {
        this.mainMenuView = mainMenuView;
        this.loginView = loginView;
    }

    @Override
    public void prepareSuccessView() {
        // Switch to the login view
        mainMenuView.setVisible(false);
        loginView.setVisible(true);
    }
}

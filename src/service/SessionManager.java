package service;

import entities.User;

public class SessionManager {
    private static SessionManager instance;
    private User currentUser;
    private boolean isGuest;

    private SessionManager() {}

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void login(User user) {
        this.currentUser = user;
        this.isGuest = false;
    }

    public void loginAsGuest() {
        this.currentUser = null;
        this.isGuest = true;
    }

    public void logout() {
        this.currentUser = null;
        this.isGuest = false;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}

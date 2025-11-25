package entities;

public class UserSession {
    private static UserSession instance;
    private User currentUser;

    private UserSession() {}

    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        if (user != null) {
            user.setLoggedIn(true);
        }
    }

    public void clearSession() {
        if (currentUser != null) {
            currentUser.setLoggedIn(false);
        }
        currentUser = null;
    }

    public boolean isUserLoggedIn() {
        return currentUser != null && currentUser.isLoggedIn();
    }
}

package use_case.user.login;

public class LoginOutputData {
    private final String username;
    private final boolean success;

    public LoginOutputData(String username, boolean success) {
        this.username = username;
        this.success = success;
    }

    public String getUsername() {
        return username;
    }

    public boolean isSuccess() {
        return success;
    }
}

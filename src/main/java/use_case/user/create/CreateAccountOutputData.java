package use_case.user.create;

public class CreateAccountOutputData {
    private final String username;
    private final boolean success;

    public CreateAccountOutputData(String username, boolean success) {
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

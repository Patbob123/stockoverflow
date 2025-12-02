package use_case.user.create;

public class CreateAccountInputData {
    private final String username;
    private final String password;

    public CreateAccountInputData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
package interface_adapter.login;

public class LoginState {
    private String username = "";
    private String usernameError = null;
    private String password = "";
    private String passwordError = null;


    public LoginState(LoginState copy) {
        username = copy.username;
        usernameError = copy.usernameError;
        password = copy.password;
        passwordError = copy.passwordError;
    }


    public LoginState() {}

    // Getters
    public String getUsername() { return username; }
    public String getUsernameError() { return usernameError; }
    public String getPassword() { return password; }
    public String getPasswordError() { return passwordError; }

    // Setters
    public void setUsername(String username) { this.username = username; }
    public void setUsernameError(String usernameError) { this.usernameError = usernameError; }
    public void setPassword(String password) { this.password = password; }
    public void setPasswordError(String passwordError) { this.passwordError = passwordError; }
}
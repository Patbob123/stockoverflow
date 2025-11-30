package interface_adapter.mainmenu;

public class MainMenuState {
    private String username = "";
    private String errorMessage = null;

    public MainMenuState(MainMenuState copy) {
        this.username = copy.username;
        this.errorMessage = copy.errorMessage;
    }

    public MainMenuState() {}

    public String getUsername() { return username; }
    public String getErrorMessage() { return errorMessage; }

    public void setUsername(String username) { this.username = username; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
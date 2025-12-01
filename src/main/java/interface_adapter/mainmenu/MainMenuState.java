package interface_adapter.mainmenu;

import interface_adapter.login.LoginViewModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * The State for a note.
 *
 * <p>For this example, a note is simplay a string.</p>
 */
public class MainMenuState {
    private String username = "your name";
    private Date date;

    @Getter
    @Setter
    private LoginViewModel loginViewModel;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

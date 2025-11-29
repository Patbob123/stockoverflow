package interface_adapter.mainmenu;

import java.util.Date;

/**
 * The State for a note.
 *
 * <p>For this example, a note is simplay a string.</p>
 */
public class MainMenuState {
    private String username = "your name";
    private Date date;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

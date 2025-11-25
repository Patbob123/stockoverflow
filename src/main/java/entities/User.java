package entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class User {
    // Getters and Setters
    private final String userID;
    @Setter
    private String username;
    @Setter
    private String password;
    @Setter
    private String email;
    @Setter
    private PortfolioList portfolioList;
    @Setter
    private List<String> searchHistory;
    @Setter
    private boolean isLoggedIn;

    public User(String userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = this.password;
        this.isLoggedIn = false;
    }

}

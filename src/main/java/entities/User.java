package entities;

import java.util.ArrayList;
import java.util.List;

public class User {
    // Getters and Setters
    private final String userID;
    @Setter
    private String username;
    @Setter
    private String password;
    @Setter
    private String email;

    private PortfolioList portfolioList;
    @Setter
    private List<String> searchHistory;
    @Setter
    private boolean isLoggedIn;

    public User(String userID, String username, String password, String email) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.portfolioList = new PortfolioList();
        this.searchHistory = new ArrayList<>();
    }

    public User(String userID, String username) {
        this(userID, username, null, null);
    }

    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PortfolioList getPortfolioList() {
        return portfolioList;
    }

    public void setPortfolioList(PortfolioList portfolioList) {
        this.portfolioList = portfolioList;
    }

    public List<String> getSearchHistory() {
        return searchHistory;
    }

    public void addSearchHistory(String ticker) {
        if (!searchHistory.contains(ticker)) {
            searchHistory.add(ticker);
        }
    }
}

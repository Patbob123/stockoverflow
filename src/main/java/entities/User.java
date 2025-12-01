package entities;

import entities.Portfolio.PortfolioList;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a registered user of the application.
 */
public class User {

    private final String userID;
    private String username;
    private String password;
    private String email;
    private PortfolioList portfolioList;
    private List<String> searchHistory;

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

    // --- Getters ---
    public String getUserID() { return userID; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public PortfolioList getPortfolioList() { return portfolioList; }
    public List<String> getSearchHistory() { return searchHistory; }

    // --- Setters ---
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
    public void setPortfolioList(PortfolioList portfolioList) { this.portfolioList = portfolioList; }
    public void setSearchHistory(List<String> searchHistory) { this.searchHistory = searchHistory; }
}
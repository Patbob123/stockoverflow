package entities;

// import lombok.Getter;
// import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.swing.*;

// @Getter
// @Setter
public class User {
    private final String userID;
    private String username;
    private String passwordHash;
    private String email;
    private PortfolioList portfolioList;
    private List<String> searchHistory;
    private Date createdAt;

    // New user regi
    public User(String username, String passwordHash, String email) {
        this.userID = generateUserID();
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.portfolioList = new PortfolioList();
        this.searchHistory = new ArrayList<>();
        this.createdAt = new Date();
    }

    // loading users from the database
    public User(String userID, String username, String passwordHash, String email,
                PortfolioList portfolioList, List<String> searchHistory, Date createdAt) {
        this.userID = userID;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.portfolioList = portfolioList;
        this.searchHistory = searchHistory;
        this.createdAt = createdAt;
    }

    private String generateUserID() {
        return UUID.randomUUID().toString();
    }

    // add history
    public void addToSearchHistory(String query) {
        searchHistory.add(0, query); // 添加到开头
        // Limit the length of search history
        if (searchHistory.size() > 100) {
            searchHistory.remove(searchHistory.size() - 1);
        }
    }

    // Getters & Setters
    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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

    public List<String> getSearchHistory() {
        return new ArrayList<>(searchHistory); // Return the copy
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}

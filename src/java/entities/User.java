package entities;

// import lombok.Getter;
// import lombok.Setter;

import java.util.List;

// @Getter
// @Setter
public class User {
    private final String userID;
    private String username;
    private String password;
    private String email;
    private PortfolioList portfolioList;
    private List<String> searchHistory;

    public User(String userID, String username/*, Date date, ImageIcon image, double high, double low, double close*/){
        this.userID = userID;
        this.username = username;
    }




}

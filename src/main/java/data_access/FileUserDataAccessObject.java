package data_access;

import entities.Portfolio.Portfolio;
import entities.User;
import entities.UserFactory;
import use_case.UserDataAccessInterface;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileUserDataAccessObject implements UserDataAccessInterface {

    private final File csvFile;
    private final Map<String, Integer> headers = new LinkedHashMap<>();
    private final Map<String, User> accounts = new HashMap<>();

    private UserFactory userFactory;

    public FileUserDataAccessObject(String csvPath, UserFactory userFactory) throws IOException {
        this.userFactory = userFactory;
        this.csvFile = new File(csvPath);

        headers.put("userID", 0);
        headers.put("username", 1);
        headers.put("password", 2);
        headers.put("email", 3);
        headers.put("portfolios", 4);

        if (csvFile.length() == 0) {
            save();
        } else {
            load();
        }
    }

    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write(String.join(",", headers.keySet()));
            writer.newLine();

            for (User user : accounts.values()) {
                String pw = user.getPassword() != null ? user.getPassword() : "";
                String em = user.getEmail() != null ? user.getEmail() : "";

                StringBuilder portfolioNames = new StringBuilder();
                if (user.getPortfolioList() != null) {
                    for (Portfolio p : user.getPortfolioList()) {
                        portfolioNames.append(p.getName()).append(";");
                    }
                }

                String line = String.format("%s,%s,%s,%s,%s",
                        user.getUserID(),
                        user.getUsername(),
                        pw,
                        em,
                        portfolioNames.toString());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void load() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String header = reader.readLine();

            String row;
            while ((row = reader.readLine()) != null) {
                String[] col = row.split(",");
                if (col.length < 4) continue;

                String userID = String.valueOf(col[headers.get("userID")]);
                String username = String.valueOf(col[headers.get("username")]);
                String password = String.valueOf(col[headers.get("password")]);
                String email = String.valueOf(col[headers.get("email")]);

                String portfolioNames = "";
                if (col.length > 4) {
                    portfolioNames = String.valueOf(col[headers.get("portfolios")]);
                }

                User user = new User(userID, username, password, email);

                if (!portfolioNames.isEmpty()) {
                    String[] names = portfolioNames.split(";");
                    for (String name : names) {
                        if (!name.trim().isEmpty()) {
                            user.getPortfolioList().addPortfolio(new Portfolio(name));
                        }
                    }
                }

                accounts.put(username, user);
            }
        }
    }

    @Override
    public void save(User user) {
        accounts.put(user.getUsername(), user);
        this.save();
    }

    @Override
    public User get(String username) {
        return accounts.get(username);
    }

    @Override
    public boolean existsByName(String username) {
        return accounts.containsKey(username);
    }
}
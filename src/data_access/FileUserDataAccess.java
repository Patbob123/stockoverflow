package data_access;

import entities.PortfolioList;
import entities.User;
import utils.PasswordUtils;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileUserDataAccess implements UserDataAccessInterface {

    private final String filePath;
    private Map<String, User> users; // memory caching

    public FileUserDataAccess(String filePath) {
        this.filePath = filePath;
        this.users = loadUsers();
    }

    private Map<String, User> loadUsers() {
        File file = new File(filePath);
        if (!file.exists()) {
            return new HashMap<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof Map) {
                return (Map<String, User>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
        return new HashMap<>();
    }

    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    @Override
    public void save(User user) {
        users.put(user.getUsername(), user);
        saveUsers();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public boolean existsByUsername(String username) {
        return users.containsKey(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return users.values().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }
}
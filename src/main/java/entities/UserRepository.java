package entities;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserRepository {
    private static UserRepository instance;
    private final Map<String, User> usersByUsername;
    private final Map<String, User> usersById;

    private UserRepository() {
        usersByUsername = new HashMap<>();
        usersById = new HashMap<>();
    }

    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public User createUser(String username, String password) {
        if (usersByUsername.containsKey(username)) {
            return null;
        }

        String userId = UUID.randomUUID().toString();
        User user = new User(userId, username, password);
        usersByUsername.put(username, user);
        usersById.put(userId, user);
        return user;
    }

    public User getUserByUsername(String username) {
        return usersByUsername.get(username);
    }

    public User getUserById(String userId) {
        return usersById.get(userId);
    }

    public boolean usernameExists(String username) {
        return usersByUsername.containsKey(username);
    }
}

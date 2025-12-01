package entities;

import java.util.UUID;

public class CommonUserFactory implements UserFactory {
    @Override
    public User create(String username, String password, String email) {
        final String userID = UUID.randomUUID().toString();
        return new User(userID, username, password, email);
    }
}
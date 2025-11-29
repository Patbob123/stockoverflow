package use_case;

import entities.User;

public interface UserDataAccessInterface {
    void save(User user);
    User get(String username);
    boolean existsByName(String username);
}
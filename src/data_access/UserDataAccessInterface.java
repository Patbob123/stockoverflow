package data_access;

import entities.User;
import java.util.Optional;

public interface UserDataAccessInterface {

    // save
    void save(User user);

    // find
    Optional<User> findByUsername(String username);

    // mail find
    Optional<User> findByEmail(String email);

    // user exist
    boolean existsByUsername(String username);

    // mail exist
    boolean existsByEmail(String email);
}
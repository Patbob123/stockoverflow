package entities;

public interface UserFactory {
    User create(String username, String password, String email);
}
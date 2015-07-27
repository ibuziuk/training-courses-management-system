package org.exadel.training.dao;

import org.exadel.training.model.User;

import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();

    List<User> getUsersByName(String firstName, String lastName);

    User getUserById(long id);

    User getUserByLogin(String login);

    void addUser(User user);

    void updateUser(User user);

    void removeUser(User user);
}

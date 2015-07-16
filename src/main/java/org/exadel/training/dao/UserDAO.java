package org.exadel.training.dao;

import org.exadel.training.model.User;

import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();

    List<User> getUsersByName(String name);

    List<User> getUsersByRole(int role);

    User getUserById(long id);

    void addUser(User user);

    void updateUser(User user);

    void removeUser(User user);
}

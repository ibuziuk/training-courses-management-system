package org.exadel.training.service;

import org.exadel.training.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    List<User> getUsersByName(String firstName, String lastName);

    User getUserById(long id);

    void addUser(User user);

    void updateUser(User user);

    void removeUser(User user);
}

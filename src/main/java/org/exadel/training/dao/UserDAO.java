package org.exadel.training.dao;

import org.exadel.training.model.User;

import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();

    List<User> getAllUsers(int pageNumber, int pageSize, String sortType, String order);

    List<User> getUsersByName(String firstName, String lastName);

    User getUserById(long id);

    User getUserByLogin(String login);

    void addUser(User user);

    void updateUser(User user);

    void removeUser(User user);

    List<User> getUsersByRole(String role);

    List<User> searchUsersByName(int pageNumber, int pageSize, String firstName, String lastName);

    List<User> searchUsersByLogin(int pageNumber, int pageSize, String login);

    List<User> searchUsersByEmail(int pageNumber, int pageSize, String email);

    List<User> searchUsersByRole(int pageNumber, int pageSize, String role);
}

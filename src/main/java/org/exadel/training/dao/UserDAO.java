package org.exadel.training.dao;

import org.exadel.training.model.User;

import java.util.List;
import java.util.Map;

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

    Map<String, Object> searchUsersByName(int pageNumber, int pageSize, String name);

    Map<String, Object> searchUsersByName(int pageNumber, int pageSize, String firstName, String lastName);

    Map<String, Object> searchUsersByLogin(int pageNumber, int pageSize, String login);

    Map<String, Object> searchUsersByEmail(int pageNumber, int pageSize, String email);

    Map<String, Object> searchUsersByRole(int pageNumber, int pageSize, String role);
}

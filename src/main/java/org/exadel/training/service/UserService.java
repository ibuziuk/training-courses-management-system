package org.exadel.training.service;

import org.exadel.training.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> getAllUsers();

    List<User> getAllUsers(int pageNumber, int pageSize, String sortType, String order);

    List<User> getUsersByName(String firstName, String lastName);

    User getUserById(long id);

    void addUser(User user);

    void updateUser(User user);

    void removeUser(User user);

    List<User> getUsersByRole(String role);

    Map<String, Object> searchUsers(int pageNumber, int pageSize, String searchType, String value);
}

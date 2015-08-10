package org.exadel.training.dao;

import org.exadel.training.model.Absence;
import org.exadel.training.model.Training;
import org.exadel.training.model.User;

import java.util.List;
import java.util.Map;

public interface UserDAO {
    List<User> getAllUsers();

    List<User> getAllUsers(int pageNumber, int pageSize, String sortType, String order);

    List<User> getUsersByName(String firstName, String lastName);

    User getUserById(long id);

    User getUserByLogin(String login);

    User getUserByEmail(String email);

    void addUser(User user);

    void updateUser(User user);

    void removeUser(User user);

    List<User> getUsersByRole(String role);

    Map<String, Object> searchUsersByName(int pageNumber, int pageSize, String name);

    Map<String, Object> searchUsersByName(int pageNumber, int pageSize, String firstName, String lastName);

    Map<String, Object> searchUsersByLogin(int pageNumber, int pageSize, String login);

    List<Absence> getAbsences(User user);

    List<Training> getLeads(User user);

    Map<String, Object> searchUsersByEmail(int pageNumber, int pageSize, String email);

    Map<String, Object> searchUsersByRole(int pageNumber, int pageSize, String role);

    List<Training> getExTrainings(long userId);
}

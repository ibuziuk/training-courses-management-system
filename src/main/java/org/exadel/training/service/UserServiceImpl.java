package org.exadel.training.service;

import org.exadel.training.dao.UserDAO;
import org.exadel.training.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    @Transactional
    public List<User> getAllUsers(int pageNumber, int pageSize, String sortType, String order) {
        return userDAO.getAllUsers(pageNumber, pageSize, sortType, order);
    }

    @Override
    @Transactional
    public List<User> getUsersByName(String firstName, String lastName) {
        return userDAO.getUsersByName(firstName, lastName);
    }

    @Override
    @Transactional
    public User getUserById(long id) {
        return userDAO.getUserById(id);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        userDAO.addUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Override
    @Transactional
    public void removeUser(User user) {
        userDAO.removeUser(user);
    }

    @Override
    @Transactional
    public List<User> getUsersByRole(String role) {
        return userDAO.getUsersByRole(role);
    }

    @Override
    @Transactional
    public List<User> searchUsers(int pageNumber, int pageSize, String searchType, String value) {
        switch (searchType) {
            case "name":
                int splitNumber = value.lastIndexOf(' ');
                return userDAO.searchUsersByName(pageNumber, pageSize, value.substring(0, splitNumber), value.substring(splitNumber + 1));
            case "login":
                return userDAO.searchUsersByLogin(pageNumber, pageSize, value);
            case "email":
                return userDAO.searchUsersByEmail(pageNumber, pageSize, value);
            case "role":
                return userDAO.searchUsersByRole(pageNumber, pageSize, value);
        }
        return null;
    }
}

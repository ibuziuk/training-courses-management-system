package org.exadel.training.dao;

import org.exadel.training.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAllUsers() {
        return sessionFactory.getCurrentSession().createQuery("FROM User ORDER BY firstName, lastName").list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public User getUserById(long id) {
        return (User) sessionFactory.getCurrentSession().get(User.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public User getUserByLogin(String login) {
        List<User> list = sessionFactory.getCurrentSession().createQuery("FROM User WHERE login = :login").setString("login", login).list();
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getUsersByName(String firstName, String lastName) {
        /*return sessionFactory.getCurrentSession().createQuery("FROM User WHERE firstName = :firstName AND lastName = :lastName").setString("firstName", firstName).setString("lastName", lastName).list();*/
        return null;
    }

    @Override
    public void addUser(User user) {
        if (user != null) {
            sessionFactory.getCurrentSession().save(user);
        }
    }

    @Override
    public void updateUser(User user) {
        if (user != null) {
            sessionFactory.getCurrentSession().merge(user);
        }
    }

    @Override
    public void removeUser(User user) {
        if (user != null) {
            sessionFactory.getCurrentSession().delete(user);
        }
    }
}

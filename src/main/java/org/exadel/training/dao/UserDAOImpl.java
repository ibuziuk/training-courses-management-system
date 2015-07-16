package org.exadel.training.dao;

import org.exadel.training.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by Alex on 14.07.2015.
 */
@Repository
public class UserDAOImpl implements UserDAO{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<User> getAllUsers() {
        return sessionFactory.getCurrentSession().createCriteria(User.class).list();
    }

    @Override
    public User getUserById(long id) {
        return (User) sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public List<User> getUsersByName(String name) {
        return sessionFactory.getCurrentSession().createQuery("FROM User u WHERE u.name = :name").setString("name", name).list();
    }

    @Override
    public List<User> getUsersByRole(int role) {
        return sessionFactory.getCurrentSession().createQuery("FROM User u WHERE u.roleID = :role").setInteger("role", role).list();
    }

    @Override
    public void addUser(User user) {
        if (user!=null) {
            sessionFactory.getCurrentSession().save(user);
        };
    }

    @Override
    public void updateUser(User user) {
        if (user!=null) {
            sessionFactory.getCurrentSession().merge(user);
        }
    }

    @Override
    public void removeUser(User user) {
        if (user!=null) {
            sessionFactory.getCurrentSession().delete(user);
        }
    }
}

package org.exadel.training.dao;

import org.exadel.training.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAllUsers() {
        Collection result = new LinkedHashSet(sessionFactory.getCurrentSession().createCriteria(User.class).list());
        return new ArrayList<>(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public User getUserById(long id) {
        return (User) sessionFactory.getCurrentSession().get(User.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getUsersByName(String name) {
        return sessionFactory.getCurrentSession().createQuery("FROM User u WHERE u.name = :name").setString("name", name).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getUsersByRole(int role) {
        return sessionFactory.getCurrentSession().createQuery("FROM User u WHERE u.roleId = :role").setInteger("role", role).list();
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

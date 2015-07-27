package org.exadel.training.dao;

import org.exadel.training.model.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        return sessionFactory.getCurrentSession().createCriteria(User.class).addOrder(Order.asc("firstName")).addOrder(Order.asc("lastName")).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public User getUserById(long id) {
        return (User) sessionFactory.getCurrentSession().get(User.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public User getUserByLogin(String login) {
        List<User> list = sessionFactory.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("login", login)).list();
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getUsersByName(String firstName, String lastName) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("firstName", firstName));
        criteria.add(Restrictions.eq("lastName", lastName));
        Collection result = new LinkedHashSet(criteria.list());
        return new ArrayList<>(result);
    }

    @Override
    public void addUser(User user) {
        if (user != null) {
            sessionFactory.getCurrentSession().persist(user);
        }
    }

    @Override
    public void updateUser(User user) {
        if (user != null) {
            sessionFactory.getCurrentSession().update(user);
        }
    }

    @Override
    public void removeUser(User user) {
        if (user != null) {
            sessionFactory.getCurrentSession().delete(user);
        }
    }
}

package org.exadel.training.dao;

import org.exadel.training.model.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAllUsers() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.addOrder(Order.asc("firstName")).addOrder(Order.asc("lastName"));
        return new ArrayList<>(new LinkedHashSet(criteria.list()));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAllUsers(int pageNumber, int pageSize, String sortType, String order) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setProjection(Projections.distinct(Projections.property("userId")))
                .setMaxResults(pageSize);

        if ("name".equals(sortType)) {
            if ("asc".equals(order)) {
                criteria.addOrder(Order.asc("firstName"))
                        .addOrder(Order.asc("lastName"));
            } else {
                criteria.addOrder(Order.desc("firstName"))
                        .addOrder(Order.desc("lastName"));
            }
        } else if ("role".equals(sortType)) {
            criteria.createAlias("roles", "role");
            if ("asc".equals(order)) {
                criteria.addOrder(Order.asc("role.role"));
            } else {
                criteria.addOrder(Order.desc("role.role"));
            }
            criteria.addOrder(Order.asc("firstName"))
                    .addOrder(Order.asc("lastName"));
        } else {
            if ("asc".equals(order)) {
                criteria.addOrder(Order.asc(sortType));
            } else {
                criteria.addOrder(Order.desc(sortType));
            }
        }

        List<Long> list = criteria.list();
        List<User> users = new ArrayList<>(list.size());
        users.addAll(list.stream().map(this::getUserById).collect(Collectors.toList()));
        return users;
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
            sessionFactory.getCurrentSession().save(user);
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

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getUsersByRole(String role) {
        Collection result = new LinkedHashSet(sessionFactory.getCurrentSession().createQuery("SELECT DISTINCT user FROM User user JOIN user.roles role WHERE role.role = :role").setParameter("role", role).list());
        return new ArrayList<>(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> searchUsersByName(int pageNumber, int pageSize, String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class)
                .setProjection(Projections.distinct(Projections.property("userId")))
                .add(Restrictions.disjunction()
                        .add(Restrictions.like("firstName", "%" + name + "%"))
                        .add(Restrictions.like("lastName", "%" + name + "%")));
        int size = criteria.list().size();
        criteria.setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize);
        List<Long> list = criteria.list();
        List<User> users = new ArrayList<>(list.size());
        users.addAll(list.stream().map(this::getUserById).collect(Collectors.toList()));
        Map<String, Object> result = new HashMap<>(2);
        result.put("users", users);
        result.put("size", size);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> searchUsersByName(int pageNumber, int pageSize, String firstName, String lastName) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class)
                .setProjection(Projections.distinct(Projections.property("userId")))
                .add(Restrictions.like("firstName", "%" + firstName + "%"))
                .add(Restrictions.like("lastName", "%" + lastName + "%"));
        int size = criteria.list().size();
        criteria.setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize);
        List<Long> list = criteria.list();
        List<User> users = new ArrayList<>(list.size());
        users.addAll(list.stream().map(this::getUserById).collect(Collectors.toList()));
        Map<String, Object> result = new HashMap<>(2);
        result.put("users", users);
        result.put("size", size);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> searchUsersByLogin(int pageNumber, int pageSize, String login) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class)
                .setProjection(Projections.distinct(Projections.property("userId")))
                .add(Restrictions.like("login", "%" + login + "%"));
        int size = criteria.list().size();
        criteria.setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize);
        List<Long> list = criteria.list();
        List<User> users = new ArrayList<>(list.size());
        users.addAll(list.stream().map(this::getUserById).collect(Collectors.toList()));
        Map<String, Object> result = new HashMap<>(2);
        result.put("users", users);
        result.put("size", size);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> searchUsersByEmail(int pageNumber, int pageSize, String email) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class)
                .setProjection(Projections.distinct(Projections.property("userId")))
                .add(Restrictions.like("email", "%" + email + "%"));
        int size = criteria.list().size();
        criteria.setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize);
        List<Long> list = criteria.list();
        List<User> users = new ArrayList<>(list.size());
        users.addAll(list.stream().map(this::getUserById).collect(Collectors.toList()));
        Map<String, Object> result = new HashMap<>(2);
        result.put("users", users);
        result.put("size", size);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> searchUsersByRole(int pageNumber, int pageSize, String role) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class)
                .setProjection(Projections.distinct(Projections.property("userId")))
                .createAlias("roles", "role")
                .add(Restrictions.like("role.role", "%" + role + "%"));
        int size = criteria.list().size();
        criteria.setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize);
        List<Long> list = criteria.list();
        List<User> users = new ArrayList<>(list.size());
        users.addAll(list.stream().map(this::getUserById).collect(Collectors.toList()));
        Map<String, Object> result = new HashMap<>(2);
        result.put("users", users);
        result.put("size", size);
        return result;
    }
}

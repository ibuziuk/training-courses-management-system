package org.exadel.training.dao;

import org.exadel.training.model.Role;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addRole(Role role) {
        if (role != null) {
            sessionFactory.getCurrentSession().persist(role);
        }
    }

    @Override
    public void removeRole(Role role) {
        if (role != null) {
            sessionFactory.getCurrentSession().delete(role);
        }
    }
}

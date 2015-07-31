package org.exadel.training.dao;

import org.exadel.training.model.Role;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Override
    public Role getRoleByName(String name) {
        List<Role> list = sessionFactory.getCurrentSession().createCriteria(Role.class).add(Restrictions.eq("role", name)).list();
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

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

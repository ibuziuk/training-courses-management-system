package org.exadel.training.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CurrentListDAOImpl implements CurrentListDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addVisitor(long userId, long trainingId) {
        sessionFactory.getCurrentSession().persist(userId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Long> getAllVisitors() {
        return sessionFactory.getCurrentSession().createCriteria(Long.class).list();
    }

    @Override
    public void removeVisitor(long userId, long trainingId) {
        sessionFactory.getCurrentSession().delete(userId);
    }
}

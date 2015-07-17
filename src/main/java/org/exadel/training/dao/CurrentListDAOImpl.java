package org.exadel.training.dao;

import org.exadel.training.model.CurrentList;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CurrentListDAOImpl implements CurrentListDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addVisitor(long userID, long trainingID) {
            sessionFactory.getCurrentSession().persist(userID);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Long> getAllVisitors() {
        return sessionFactory.getCurrentSession().createCriteria(Long.class).list();
    }

    @Override
    public void removeVisitor(long userID, long trainingID) {
            sessionFactory.getCurrentSession().delete(userID);
    }
}

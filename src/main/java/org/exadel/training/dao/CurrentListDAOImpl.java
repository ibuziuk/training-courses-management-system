package org.exadel.training.dao;

import org.exadel.training.model.CurrentList;
import org.exadel.training.model.Training;
import org.exadel.training.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CurrentListDAOImpl implements CurrentListDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addVisitor(User visitor, Training training) {
        if (visitor != null && training != null) {
            CurrentList currentList = new CurrentList();
            currentList.setTraining(training);
            currentList.setVisitor(visitor);
            sessionFactory.getCurrentSession().persist(currentList);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Long> getAllVisitors() {
        return sessionFactory.getCurrentSession().createCriteria(Long.class).list();
    }

    @Override
    public void removeVisitor(User visitor, Training training) {
        if (visitor != null && training != null) {
            CurrentList currentList = new CurrentList();
            currentList.setVisitor(visitor);
            currentList.setTraining(training);
            sessionFactory.getCurrentSession().delete(currentList);
        }
    }
}

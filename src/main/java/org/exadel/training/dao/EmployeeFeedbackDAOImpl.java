package org.exadel.training.dao;

import org.exadel.training.model.EmployeeFeedback;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeFeedbackDAOImpl implements EmployeeFeedbackDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addFeedback(EmployeeFeedback feedback) {
        if (feedback != null) {
            sessionFactory.getCurrentSession().persist(feedback);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<EmployeeFeedback> getAllFeedbacks() {
        return sessionFactory.getCurrentSession().createCriteria(EmployeeFeedback.class).list();
    }
}

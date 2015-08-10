package org.exadel.training.dao;

import org.exadel.training.model.EmployeeFeedback;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
    public List<EmployeeFeedback> getFeedbacksByUserAndTraining(long userId, long trainingId) {
        return sessionFactory.getCurrentSession().createCriteria(EmployeeFeedback.class)
                .add(Restrictions.eq("user.userId", userId))
                .add(Restrictions.eq("training.trainingId", trainingId))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<EmployeeFeedback> getAllFeedbacks() {
        return sessionFactory.getCurrentSession().createCriteria(EmployeeFeedback.class).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<EmployeeFeedback> getFeedbacksByUser(long userId) {
        return sessionFactory.getCurrentSession().createCriteria(EmployeeFeedback.class)
                .add(Restrictions.eq("user.userId", userId))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }
}

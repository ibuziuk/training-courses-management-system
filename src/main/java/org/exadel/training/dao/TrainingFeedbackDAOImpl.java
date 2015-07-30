package org.exadel.training.dao;

import org.exadel.training.model.TrainingFeedback;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainingFeedbackDAOImpl implements TrainingFeedbackDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addFeedback(TrainingFeedback feedback) {
        if (feedback != null) {
            sessionFactory.getCurrentSession().persist(feedback);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TrainingFeedback> getAllFeedbacks() {
        return sessionFactory.getCurrentSession().createCriteria(TrainingFeedback.class).list();
    }

    @Override
    public boolean containsUserByTraining(long trainingId, long userId) {
        List list = sessionFactory.getCurrentSession().createCriteria(TrainingFeedback.class)
                .add(Restrictions.eq("training.trainingId", trainingId))
                .add(Restrictions.eq("user.userId", userId))
                .list();
        return (list.size() != 0);
    }
}

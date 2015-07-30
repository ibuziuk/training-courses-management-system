package org.exadel.training.dao;

import org.exadel.training.model.TrainingFeedback;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
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

    @Override
    public boolean containsUserByTraining(long trainingId, long userId) {
        List list = sessionFactory.getCurrentSession().createCriteria(TrainingFeedback.class)
                .add(Restrictions.eq("training.trainingId", trainingId))
                .add(Restrictions.eq("user.userId", userId))
                .list();
        return (list.size() != 0);
    }

    @Override
    public double getAverageRatingByTrainingID(long trainingId) {
        List list = sessionFactory.getCurrentSession().createCriteria(TrainingFeedback.class)
                .add(Restrictions.eq("training.trainingId", trainingId))
                .setProjection(Projections.avg("starCount"))
                .list();
        if (list.contains(null)) {
            return -1.;
        }
        return (double) list.get(0);
    }
}

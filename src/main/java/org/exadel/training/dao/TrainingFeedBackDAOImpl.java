package org.exadel.training.dao;

import org.exadel.training.model.TrainingFeedback;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainingFeedbackDAOImpl implements TrainingFeedbackDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addFeedback(TrainingFeedback feedBack) {
        if (feedBack != null) {
            sessionFactory.getCurrentSession().persist(feedBack);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TrainingFeedback> getAllFeedbacks() {
        return sessionFactory.getCurrentSession().createCriteria(TrainingFeedback.class).list();
    }
}

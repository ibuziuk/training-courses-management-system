package org.exadel.training.dao;

import org.exadel.training.model.TrainingFeed_back;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainingFeed_backDAOImpl implements TrainingFeed_backDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addFeedback(TrainingFeed_back feedback) {
        if (feedback != null) {
            sessionFactory.getCurrentSession().persist(feedback);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TrainingFeed_back> getAllFeedbacks() {
        return sessionFactory.getCurrentSession().createCriteria(TrainingFeed_back.class).list();
    }
}

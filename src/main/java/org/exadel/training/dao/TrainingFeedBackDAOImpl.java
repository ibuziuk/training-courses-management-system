package org.exadel.training.dao;

import org.exadel.training.model.TrainingFeedBack;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainingFeedBackDAOImpl implements TrainingFeedBackDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addFeedBack(TrainingFeedBack feedBack) {
        if (feedBack != null) {
            sessionFactory.getCurrentSession().persist(feedBack);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TrainingFeedBack> getAllFeedBacks() {
        return sessionFactory.getCurrentSession().createCriteria(TrainingFeedBack.class).list();
    }
}

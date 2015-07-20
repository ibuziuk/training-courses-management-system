package org.exadel.training.dao;

import org.exadel.training.model.TrainingRating;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingRatingDAOImpl implements TrainingRatingDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addTrainingRating(TrainingRating trainingRating) {
        if (trainingRating != null) {
            sessionFactory.getCurrentSession().persist(trainingRating);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public TrainingRating getAverageRatingByTrainingID(long trainingID) {
        //TODO
//        return sessionFactory.getCurrentSession()
//                .createQuery("FROM TrainingRating t WHERE t.trainingID = :trainingID");
        return null;
    }
}

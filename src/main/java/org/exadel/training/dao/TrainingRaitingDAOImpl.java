package org.exadel.training.dao;

import org.exadel.training.model.TrainingRaiting;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainingRaitingDAOImpl implements TrainingRaitingDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addTrainingRaiting(TrainingRaiting trainingRaiting) {
        if (trainingRaiting != null) {
            sessionFactory.getCurrentSession().persist(trainingRaiting);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public TrainingRaiting getAverageRaitingByTrainingID(long trainingID) {
        //TODO
//        return sessionFactory.getCurrentSession()
//                .createQuery("FROM TrainingRaiting t WHERE t.trainingID = :trainingID");
        return null;
    }
}

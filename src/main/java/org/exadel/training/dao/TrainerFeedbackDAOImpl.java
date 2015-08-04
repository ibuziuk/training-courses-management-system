package org.exadel.training.dao;

import org.exadel.training.model.TrainerFeedback;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TrainerFeedbackDAOImpl implements TrainerFeedbackDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void addTrainerFeedback(TrainerFeedback trainerFeedback) {
        sessionFactory.getCurrentSession().persist(trainerFeedback);
    }
}

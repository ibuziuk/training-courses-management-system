package org.exadel.training.dao;

import org.exadel.training.model.Training;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainingDAOImpl implements TrainingDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addTraining(Training training){
        sessionFactory.getCurrentSession().persist(training);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List getAllTraining(){
        return sessionFactory.getCurrentSession().createQuery("from training").list();
    }

    @Override
    public void updateTraining(Training training){
        sessionFactory.getCurrentSession().update(training);
    }

    @Override
    public void removeTraining(Training training){
        sessionFactory.getCurrentSession().delete(training);
    }
}

//TODO: check (training != null)

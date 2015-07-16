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
    public void addTraining(Training training) {
        if (training != null) {
            sessionFactory.getCurrentSession().persist(training);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List getAllTraining() {
        return sessionFactory.getCurrentSession().createCriteria(Training.class).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Training> getTrainingsByName(String name) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Training t WHERE t.name = :name").setString("name", name).list();
    }

    @Override
    public void updateTraining(Training training) {
        if (training != null) {
            sessionFactory.getCurrentSession().update(training);
        }
    }

    @Override
    public void removeTraining(Training training) {
        if (training != null) {
            sessionFactory.getCurrentSession().delete(training);
        }
    }
}

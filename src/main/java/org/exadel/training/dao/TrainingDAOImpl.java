package org.exadel.training.dao;

import org.exadel.training.model.Training;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
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
    public List<Training> getAllTrainings() {
        Collection result = new LinkedHashSet(sessionFactory.getCurrentSession().createCriteria(Training.class).list());
        List<Training> list = new ArrayList<>(result);
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Training> getFutureTrainings() {
        Collection result = new LinkedHashSet(sessionFactory.getCurrentSession().createQuery("FROM Training t WHERE t.date >= current_timestamp ").list());
        List<Training> list = new ArrayList<>(result);
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Training> getPastTrainings() {
        Collection result = new LinkedHashSet(sessionFactory.getCurrentSession().createQuery("FROM Training t WHERE t.date <= current_timestamp ").list());
        List<Training> list = new ArrayList<>(result);
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Training> getTrainingsByName(String name) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Training t WHERE t.title = :name").setString("name", name).list();
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

    @Override
    public Training getTrainingById(long id) {
        return (Training) sessionFactory.getCurrentSession().get(Training.class, id);
    }
}

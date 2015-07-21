package org.exadel.training.dao;

import org.exadel.training.model.TrainingTag;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainingTagDAOImpl implements TrainingTagDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addTrainingTag(TrainingTag trainingTag) {
        if (trainingTag != null) {
            sessionFactory.getCurrentSession().persist(trainingTag);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TrainingTag> getAllTrainingTags() {
        return sessionFactory.getCurrentSession().createCriteria(TrainingTag.class).list();
    }
}

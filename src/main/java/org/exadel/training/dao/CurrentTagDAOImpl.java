package org.exadel.training.dao;

import org.exadel.training.model.TrainingTag;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CurrentTagDAOImpl implements CurrentTagDAO{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addCurrentTag(TrainingTag trainingTag){
        if (trainingTag != null){
            sessionFactory.getCurrentSession().persist(trainingTag);
        }
    }

    @Override
    public List<TrainingTag> getAllCurrentTags(){
        return sessionFactory.getCurrentSession().createCriteria(TrainingTag.class).list();
    }
}

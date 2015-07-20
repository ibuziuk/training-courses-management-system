package org.exadel.training.dao;

import org.exadel.training.model.TrainingAudience;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainingAudienceDAOImpl implements TrainingAudienceDAO{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addTrainingAudience(TrainingAudience trainingAudience){
        if(trainingAudience != null){
            sessionFactory.getCurrentSession().persist(trainingAudience);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TrainingAudience> getAllTrainingAudience(){
        return sessionFactory.getCurrentSession().createCriteria(TrainingAudience.class).list();
    }
}

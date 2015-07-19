package org.exadel.training.dao;

import org.exadel.training.model.CurrentTag;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CurrentTagDAOImpl implements CurrentTagDAO{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addCurrentTag(CurrentTag currentTag){
        if (currentTag != null){
            sessionFactory.getCurrentSession().persist(currentTag);
        }
    }

    @Override
    public List<CurrentTag> getAllCurrentTags(){
        return sessionFactory.getCurrentSession().createCriteria(CurrentTag.class).list();
    }
}

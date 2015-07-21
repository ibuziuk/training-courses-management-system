package org.exadel.training.dao;

import org.exadel.training.model.Audience;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AudienceDAOImpl implements AudienceDAO{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addAudience(Audience audience){
        if(audience != null){
            sessionFactory.getCurrentSession().persist(audience);
        }
    }
}

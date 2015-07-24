package org.exadel.training.dao;

import org.exadel.training.model.Audience;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AudienceDAOImpl implements AudienceDAO{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Audience getAudienceByValue(String value) {
        if (value !=null) {
            Audience audience;
            try {
                audience = (Audience) sessionFactory.getCurrentSession().createQuery("FROM Audience u WHERE u.value = :value").setString("value", value).list().get(0);
            } catch (Exception e) {
                return null;
            }
            return audience;
        }
        return null;
    }

    @Override
    public List<Audience> getAllAudiences() {
        return sessionFactory.getCurrentSession().createCriteria(Audience.class).list();
    }

    @Override
    public void addAudience(Audience audience){
        if(audience != null){
            sessionFactory.getCurrentSession().persist(audience);
        }
    }
}

package org.exadel.training.dao;

import org.exadel.training.model.Audience;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

@Repository
public class AudienceDAOImpl implements AudienceDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Audience getAudienceByValue(String value) {
        List<Audience> list = sessionFactory.getCurrentSession().createQuery("FROM Audience t WHERE t.value = :value").setString("value", value).list();
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public void addAudience(Audience audience) {
        if (audience != null) {
            sessionFactory.getCurrentSession().persist(audience);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Audience> getAllAudience() {
        Collection result = new LinkedHashSet(sessionFactory.getCurrentSession().createCriteria(Audience.class).list());
        return new ArrayList<>(result);
    }

    @Override
    public void updateAudience(Audience audience) {
        if (audience != null) {
            sessionFactory.getCurrentSession().update(audience);
        }
    }
}

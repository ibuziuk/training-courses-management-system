package org.exadel.training.dao;

import org.exadel.training.model.Language;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

@Repository
public class LanguageDAOImpl implements LanguageDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addLanguage(Language language) {
        if (language != null) {
            sessionFactory.getCurrentSession().persist(language);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Language getLanguageByValue(String value) {
        List<Language> list = sessionFactory.getCurrentSession().createQuery("FROM Language t WHERE t.value = :value").setString("value", value).list();
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Language> getAllLanguages() {
        Collection result = new LinkedHashSet(sessionFactory.getCurrentSession().createCriteria(Language.class).list());
        return new ArrayList<>(result);
    }
}

package org.exadel.training.dao;

import org.exadel.training.model.Language;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LanguageDAOImpl implements LanguageDAO{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addLanguage(Language language){
        if (language != null){
            sessionFactory.getCurrentSession().persist(language);
        }
    }
}

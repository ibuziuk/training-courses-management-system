package org.exadel.training.dao;

import org.exadel.training.model.Tag;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TagDAOImpl implements TagDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addTag(Tag tag) {
        if (tag != null) {
            sessionFactory.getCurrentSession().persist(tag);
        }
    }

    @Override
    public Tag getTagById(int id) {
        return (Tag) sessionFactory.getCurrentSession().get(Tag.class, id);
    }
}

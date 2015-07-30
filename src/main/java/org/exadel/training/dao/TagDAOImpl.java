package org.exadel.training.dao;

import org.exadel.training.model.Tag;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

@Repository
public class TagDAOImpl implements TagDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Tag getTagByName(String name) {
        List<Tag> list = sessionFactory.getCurrentSession().createQuery("FROM Tag t WHERE t.name = :name").setString("name", name).list();
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

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

    @SuppressWarnings("unchecked")
    @Override
    public List<Tag> getAllTags() {
        Collection result = new LinkedHashSet(sessionFactory.getCurrentSession().createCriteria(Tag.class).list());
        return new ArrayList<>(result);
    }
}

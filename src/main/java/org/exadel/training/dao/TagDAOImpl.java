package org.exadel.training.dao;

import org.exadel.training.model.Tag;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagDAOImpl implements TagDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Tag> getAllTags() {
        return sessionFactory.getCurrentSession().createCriteria(Tag.class).list();
    }

    @Override
    public Tag getTagByName(String name) {
        if (name !=null) {
            Tag tag;
            try {
                tag = (Tag) sessionFactory.getCurrentSession().createQuery("FROM Tag u WHERE u.name = :name").setString("name", name).list().get(0);
            } catch (Exception e) {
                return null;
            }
            return tag;
        }
        return null;
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
}

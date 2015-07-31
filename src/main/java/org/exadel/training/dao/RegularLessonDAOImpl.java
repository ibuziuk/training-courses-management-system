package org.exadel.training.dao;

import org.exadel.training.model.RegularLesson;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

@Repository
public class RegularLessonDAOImpl implements RegularLessonDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addRegularLesson(RegularLesson regularLesson) {
        if (regularLesson != null) {
            sessionFactory.getCurrentSession().persist(regularLesson);
        }
    }

    @Override
    public void updateRegularLesson(RegularLesson regularLesson) {
        if (regularLesson != null) {
            sessionFactory.getCurrentSession().update(regularLesson);
        }
    }

    @Override
    public void removeRegularLesson(RegularLesson regularLesson) {
        if (regularLesson != null) {
            sessionFactory.getCurrentSession().delete(regularLesson);
        }
    }

    @Override
    public RegularLesson getRegularLessonById(long id) {
        return (RegularLesson) sessionFactory.getCurrentSession().get(RegularLesson.class, id);
    }

    @Override
    public List<RegularLesson> getFutureRegularLessons() {
        Collection result = new LinkedHashSet(sessionFactory.getCurrentSession().createQuery("FROM RegularLesson t WHERE t.date >= current_timestamp ").list());
        return new ArrayList<>(result);
    }
}

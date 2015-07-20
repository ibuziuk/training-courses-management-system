package org.exadel.training.dao;

import org.exadel.training.model.RegularLesson;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public RegularLesson getRegularLessonById(long id){
        return (RegularLesson) sessionFactory.getCurrentSession().get(RegularLesson.class, id);
    }
}

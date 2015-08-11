package org.exadel.training.dao;

import org.exadel.training.model.AbsenceLesson;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AbsenceLessonDAOImpl implements AbsenceLessonDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void addAbsence(AbsenceLesson absence) {
        if (absence != null) {
            sessionFactory.getCurrentSession().persist(absence);
        }
    }

    @Override
    public void updateAbsence(AbsenceLesson absence) {
        if (absence != null) {
            sessionFactory.getCurrentSession().update(absence);
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AbsenceLesson> getAllAbsences() {
        return sessionFactory.getCurrentSession().createCriteria(AbsenceLesson.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AbsenceLesson> getAbsencesByUser(long userId) {
        return sessionFactory.getCurrentSession().createCriteria(AbsenceLesson.class)
                .add(Restrictions.eq("user.userId", userId))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AbsenceLesson> getAbsencesByUserAndTraining(long userId, long trainingId) {
        return sessionFactory.getCurrentSession().createCriteria(AbsenceLesson.class)
                .add(Restrictions.eq("user.userId", userId))
                .add(Restrictions.eq("training.trainingId", trainingId))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AbsenceLesson> getAbsencesByTraining(long trainingId) {
        return sessionFactory.getCurrentSession().createCriteria(AbsenceLesson.class)
                .add(Restrictions.eq("training.trainingId", trainingId))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }
}

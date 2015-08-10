package org.exadel.training.dao;

import org.exadel.training.model.RegularLesson;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
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

    @SuppressWarnings("unchecked")
    @Override
    public List<RegularLesson> getFutureRegularLessons() {
        Collection result = new LinkedHashSet(sessionFactory.getCurrentSession().createQuery("FROM RegularLesson t WHERE t.date >= current_timestamp ").list());
        return new ArrayList<>(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RegularLesson> getSomeFutureLessonsByTraining(long trainingId, int count) {
        Timestamp date = (Timestamp) sessionFactory.getCurrentSession().createSQLQuery("SELECT CURRENT_TIMESTAMP;").uniqueResult();
        return sessionFactory.getCurrentSession().createCriteria(RegularLesson.class)
                .add(Restrictions.eq("training.trainingId", trainingId))
                .add(Restrictions.ge("date", date))
                .addOrder(Order.asc("date"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .setMaxResults(count)
                .list();
    }
}

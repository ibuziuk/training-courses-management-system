package org.exadel.training.dao;

import org.exadel.training.model.Absence;
import org.exadel.training.model.AbsenceLesson;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AbsenceLessonDAOImpl implements AbsenceLessonDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void addAbsence(Absence absence) {
        if (absence != null) {
            sessionFactory.getCurrentSession().persist(absence);
        }
    }

    @Override
    public void updateAbsence(Absence absence) {
        if (absence != null) {
            sessionFactory.getCurrentSession().update(absence);
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Absence> getAllAbsences() {
        return sessionFactory.getCurrentSession().createCriteria(AbsenceLesson.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }
}

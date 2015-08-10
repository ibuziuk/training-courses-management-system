package org.exadel.training.dao;

import org.exadel.training.model.Absence;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AbsenceDAOImpl implements AbsenceDAO {
    @Autowired
    private SessionFactory sessionFactory;

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
        return sessionFactory.getCurrentSession().createCriteria(Absence.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Absence> getAbsencesByUser(long userId) {
        return sessionFactory.getCurrentSession().createCriteria(Absence.class)
                .add(Restrictions.eq("user.userId", userId))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    @Override
    public Absence getAbsenceByUserAndTraining(long userId, long trainingId) {
        return (Absence) sessionFactory.getCurrentSession().createCriteria(Absence.class)
                .add(Restrictions.eq("user.userId", userId))
                .add(Restrictions.eq("training.trainingId", trainingId))
                .uniqueResult();
    }
}

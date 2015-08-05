package org.exadel.training.dao;

import org.exadel.training.model.TrainingEdit;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainingEditDaoImpl implements TrainingEditDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void addEdit(TrainingEdit trainingEdit) {
        sessionFactory.getCurrentSession().persist(trainingEdit);
    }

    @Override
    public void updateEdit(TrainingEdit trainingEdit) {
        sessionFactory.getCurrentSession().update(trainingEdit);
    }

    @Override
    public TrainingEdit getEditByTrainingIfExist(long trainingId) {
        return (TrainingEdit) sessionFactory.getCurrentSession().createCriteria(TrainingEdit.class)
                .add(Restrictions.eq("training.trainingId", trainingId))
                .add(Restrictions.or(
                        Restrictions.eq("isApproved", false),
                        Restrictions.isNull("isApproved")
                ))
                .uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TrainingEdit> getAllEditsByTRaining(long trainingId) {
        return sessionFactory.getCurrentSession().createCriteria(TrainingEdit.class)
                .add(Restrictions.eq("training.trainingId", trainingId))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }
}

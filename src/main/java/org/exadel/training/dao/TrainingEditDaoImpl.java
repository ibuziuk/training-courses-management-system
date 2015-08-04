package org.exadel.training.dao;

import org.exadel.training.model.TrainingEdit;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
                .uniqueResult();
    }
}

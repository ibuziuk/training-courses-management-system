package org.exadel.training.dao;

import org.exadel.training.model.Training;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

@Repository
public class TrainingDAOImpl implements TrainingDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addTraining(Training training) {
        if (training != null) {
            sessionFactory.getCurrentSession().persist(training);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Training> getAllTrainings() {
        Collection result = new LinkedHashSet(sessionFactory.getCurrentSession().createCriteria(Training.class).list());
        return new ArrayList<>(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Training> getFutureTrainings() {
        Collection result = new LinkedHashSet(sessionFactory.getCurrentSession().createQuery("FROM Training t WHERE t.date >= current_timestamp ").list());
        return new ArrayList<>(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Training> getPastTrainings() {
        Collection result = new LinkedHashSet(sessionFactory.getCurrentSession().createQuery("FROM Training t WHERE t.date <= current_timestamp ").list());
        return new ArrayList<>(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Training> getTrainingsByName(String name) {
        Collection result = new LinkedHashSet(sessionFactory.getCurrentSession().createCriteria(Training.class)
                .add(Restrictions.eq("title", name)).list());
        return new ArrayList<>(result);
    }

    @Override
    public void updateTraining(Training training) {
        if (training != null) {
            sessionFactory.getCurrentSession().update(training);
        }
    }

    @Override
    public void removeTrainingById(long id) {
        Training training = getTrainingById(id);
        if (training != null) {
            sessionFactory.getCurrentSession().delete(training);
        }
    }

    @Override
    public Training getTrainingById(long id) {
        return (Training) sessionFactory.getCurrentSession().get(Training.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Training> getTrainingsByTrainer(long id) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Training.class);
        criteria.add(Restrictions.eq("trainer.id", id));
        Collection result = new LinkedHashSet(criteria.list());
        return new ArrayList<>(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Training> getTrainingsByVisitor(long id) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Training.class);
        criteria.createAlias("visitors", "visitorsAlias").add(Restrictions.eq("visitorsAlias.userId", id));
        Collection result = new LinkedHashSet(criteria.list());
        return new ArrayList<>(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Training> getContinuousTrainings(long id){
        Training training = getTrainingById(id);
        String title = training.getTitle();
        String name = title.substring(0, title.lastIndexOf("#"));
        return sessionFactory.getCurrentSession().createCriteria(Training.class)
                .add(Restrictions.like("title", name + "#%"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }
}

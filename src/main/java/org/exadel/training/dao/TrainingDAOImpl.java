package org.exadel.training.dao;

import org.exadel.training.model.Training;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
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
    public List<Training> getFutureTrainingsForScheduling() {
        Collection result = new LinkedHashSet(sessionFactory.getCurrentSession().createQuery("FROM Training t WHERE t.date >= current_timestamp ").list());
        return new ArrayList<>(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Training> getComeTrainings(String come, boolean admin) {
        Timestamp date = (Timestamp) sessionFactory.getCurrentSession().createSQLQuery("SELECT CURRENT_TIMESTAMP").list().get(0);
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Training.class);
        if (come.equals("future")) {
            criteria.add(Restrictions.or(
                    Restrictions.ge("date", date),
                    Restrictions.ge("start", date)
            ));
        } else {
            criteria.add(Restrictions.or(
                    Restrictions.le("date", date),
                    Restrictions.le("start", date)
            ));
        }
        if (!admin) {
            criteria.add(Restrictions.eq("isApproved", true));
        }
        Collection result = new LinkedHashSet<>(criteria.list());
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

    // This method will be changed.
    @SuppressWarnings("unchecked")
    @Override
    public List<Training> getSomeTrainingOrderBy(String come, int pageNum, int pageSize, String sorting, String order, boolean admin) {
        Timestamp date = (Timestamp) sessionFactory.getCurrentSession().createSQLQuery("SELECT CURRENT_TIMESTAMP").list().get(0);
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Training.class)
//                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .setFirstResult((pageNum - 1) * pageSize)
                .setProjection(Projections.distinct(Projections.property("trainingId")));
        criteria.setMaxResults(pageSize);
        if (come.equals("future")) {
            criteria.add(Restrictions.or(
                    Restrictions.ge("date", date),
                    Restrictions.ge("end", date)
            ));
        } else {
            criteria.add(Restrictions.or(
                    Restrictions.le("date", date),
                    Restrictions.le("end", date)
            ));
        }
        if (!sorting.equals("trainerName")) {
            if (order.equals("asc")) {
                criteria.addOrder(Order.asc(sorting));
            } else {
                criteria.addOrder(Order.desc(sorting));
            }
        } else {
            criteria.createAlias("trainer", "t");
            if (order.equals("asc")) {
                criteria.addOrder(Order.asc("t.firstName"))
                        .addOrder(Order.asc("t.lastName"));
            } else {
                criteria.addOrder(Order.desc("t.firstName"))
                        .addOrder(Order.desc("t.lastName"));
            }
        }
        if (!admin) {
            criteria.add(Restrictions.eq("isApproved", true));
        }
        List<Long> id = criteria.list();
        List<Training> result = new ArrayList<>(id.size());
        for (long l : id) {
            result.add(getTrainingById(l));
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Training> searchTrainingsByTitle(String value) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Training.class)
                .add(Restrictions.like("title", "%" + value + "%"));
        Collection result = new LinkedHashSet<>(criteria.list());
        return new ArrayList<>(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Training> searchTrainingsByDate(Timestamp date) {
        Collection result = new LinkedHashSet<>(sessionFactory.getCurrentSession().createCriteria(Training.class)
                .add(Restrictions.eq("date", date))
                .list());
        return new ArrayList<>(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Training> searchTrainingsByTime(String time) {
        Collection result = new LinkedHashSet<>(sessionFactory.getCurrentSession().createCriteria(Training.class)
                .add(Restrictions.like("time", time + "%"))
                .list());
        return new ArrayList<>(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Training> searchTrainingsByLocation(int location) {
        Collection result = new LinkedHashSet<>(sessionFactory.getCurrentSession().createCriteria(Training.class)
                .add(Restrictions.eq("location", location))
                .list());
        return new ArrayList<>(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Training> searchTrainingsByTrainerName(String firstName, String lastName) {
        Collection result = new LinkedHashSet<>(sessionFactory.getCurrentSession().createCriteria(Training.class)
                .createAlias("trainer", "t")
                .add(Restrictions.like("t.firstName", "%" + firstName + "%"))
                .add(Restrictions.like("t.lastName", "%" + lastName + "%"))
                .list());
        return new ArrayList<>(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Training> getContinuousTrainings(long id) {
        Training training = getTrainingById(id);
        String title = training.getTitle();
        String name = title.substring(0, title.lastIndexOf("#"));
        return sessionFactory.getCurrentSession().createCriteria(Training.class)
                .add(Restrictions.like("title", name + "#%"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }
}

package org.exadel.training.dao;

import org.exadel.training.model.Training;
import org.exadel.training.model.User;
import org.exadel.training.model.WaitingList;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class WaitingListDAOImpl implements WaitingListDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private TrainingDAO trainingDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    public String addVisitor(long trainingID, long userID) {
        if (!checkingExist(trainingID, userID)) {
            Training training = trainingDAO.getTrainingById(trainingID);
            User user = userDAO.getUserById(userID);
            List<Training> trainings = new ArrayList<>(1);
            trainings.add(training);
            if (training.getContinuous()){
                trainings = trainingDAO.getContinuousTrainings(trainingID);
            }
            for (Training elem : trainings){
                WaitingList waitingList = new WaitingList();
                waitingList.setTraining(elem);
                waitingList.setUser(user);
                waitingList.setDate(new Timestamp(new Date().getTime()));
                sessionFactory.getCurrentSession().persist(waitingList);
            }
            return "Adding for waiting-list.";
        }
        return "Already exist in waiting-list.";
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Long> getAllVisitors() {
        return sessionFactory.getCurrentSession().createCriteria(Long.class).list();
    }

    @Override
    public String removeVisitor(long trainingID, long userID) {
        Training training = trainingDAO.getTrainingById(trainingID);
        List<Training> trainings = new ArrayList<>(1);
        trainings.add(training);
        if (training.getContinuous()){
            trainings = trainingDAO.getContinuousTrainings(trainingID);
        }
        List list = sessionFactory.getCurrentSession().createCriteria(WaitingList.class)
                .add(Restrictions.eq("training.trainingId", trainingID))
                .add(Restrictions.eq("user.userId", userID))
                .list();
        if (list.size() != 0) {
            for (Training elem : trainings){
                WaitingList wl = (WaitingList) sessionFactory.getCurrentSession().createCriteria(WaitingList.class)
                        .add(Restrictions.eq("training.trainingId", elem.getTrainingId()))
                        .add(Restrictions.eq("user.userId", userID))
                        .uniqueResult();
                sessionFactory.getCurrentSession().delete(wl);
            }
            return "Remove from waiting-list.";
        }
        return "Record does not exist.";
    }

    public boolean checkingExist(long trainingId, long userId) {
        List list = sessionFactory.getCurrentSession().createCriteria(WaitingList.class)
                .add(Restrictions.eq("user.userId", userId))
                .add(Restrictions.eq("training.trainingId", trainingId))
                .list();
        return (list.size() != 0);
    }

    public User getNext(long trainingId){
        List result = sessionFactory.getCurrentSession().createCriteria(WaitingList.class)
                .createAlias("training", "t")
                .add(Restrictions.eq("t.trainingId", trainingId))
                .addOrder(Order.asc("date"))
                .list();
        if (result.size() != 0){
            WaitingList wl = (WaitingList) result.get(0);
            User user = wl.getUser();
            removeVisitor(trainingId, user.getUserId());
            return user;
        }
        return null;
    }
}

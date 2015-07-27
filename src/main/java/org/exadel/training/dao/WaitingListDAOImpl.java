package org.exadel.training.dao;

import org.exadel.training.model.WaitingList;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
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
    public void addVisitor(long trainingID, long userID) {
        WaitingList waitingList = new WaitingList();
        waitingList.setTraining(trainingDAO.getTrainingById(trainingID));
        waitingList.setUser(userDAO.getUserById(userID));
        waitingList.setDate(new Timestamp(new Date().getTime()));
        sessionFactory.getCurrentSession().persist(waitingList);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Long> getAllVisitors() {
        return sessionFactory.getCurrentSession().createCriteria(Long.class).list();
    }

    @Override
    public void removeVisitor(long trainingID, long userID) {
        //not checked
        List list = sessionFactory.getCurrentSession().createCriteria(WaitingList.class)
                .add(Restrictions.eq("training.trainingId", trainingID))
                .add(Restrictions.eq("user.userId", userID))
                .list();
        if(list.size() != 0){
            sessionFactory.getCurrentSession().delete(list.get(0));
        }
    }
}

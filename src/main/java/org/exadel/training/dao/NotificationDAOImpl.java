package org.exadel.training.dao;

import org.exadel.training.model.Notification;
import org.exadel.training.service.TrainingService;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

@Repository
public class NotificationDAOImpl implements NotificationDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private TrainingService trainingService;

    @Override
    public void addNotification(long trainingId, long userId, int type) {
        Notification notification = new Notification();
        notification.setDate(new Timestamp(new Date().getTime()));
        notification.setTrainingId(trainingId);
        notification.setTrainingName(trainingService.getTrainingById(trainingId).getTitle());
        notification.setUserId(userId);
        notification.setType(type);
        sessionFactory.getCurrentSession().persist(notification);
    }

    @Override
    public Notification getNotificationById(long notificationId) {
        return (Notification) sessionFactory.getCurrentSession().get(Notification.class, notificationId);
    }

    @Override
    public void removeNotification(Notification notification) {
        if (notification != null) {
            sessionFactory.getCurrentSession().delete(notification);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Notification> getAllNotificationsByUser(long userId, long token) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Notification.class)
                .add(Restrictions.eq("userId", userId))
                .add(Restrictions.gt("notificationId", token));
        Set<Notification> result = new LinkedHashSet<>(criteria.list());
        return new ArrayList<>(result);
    }
}

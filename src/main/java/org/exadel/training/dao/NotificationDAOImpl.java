package org.exadel.training.dao;

import org.exadel.training.model.Notification;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

@Repository
public class NotificationDAOImpl implements NotificationDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addNotification(Notification notification) {
        if (notification != null) {
            sessionFactory.getCurrentSession().persist(notification);
        }
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
    public List<Notification> getAllNotificationsByUser(long userId) {
        Collection result = new LinkedHashSet(sessionFactory.getCurrentSession().createCriteria(Notification.class)
                .add(Restrictions.eq("userId", userId)).list());
        return new ArrayList<>(result);
    }
}

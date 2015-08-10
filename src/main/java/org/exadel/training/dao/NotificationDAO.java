package org.exadel.training.dao;

import org.exadel.training.model.Notification;

import java.util.List;

public interface NotificationDAO {
    void addNotification(long trainingId,long userId, int type);

    Notification getNotificationById(long notificationId);

    void removeNotification(Notification notification);

    List<Notification> getAllNotificationsByUser(long userId, long token);
}

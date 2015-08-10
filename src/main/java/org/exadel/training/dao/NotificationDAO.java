package org.exadel.training.dao;

import org.exadel.training.model.Notification;

import java.util.List;

public interface NotificationDAO {
    void addNotification(Notification notification);

    Notification getNotificationById(long notificationId);

    void removeNotification(Notification notification);

    List<Notification> getAllNotificationsByUser(long userId);
}

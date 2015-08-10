package org.exadel.training.service;

import org.exadel.training.model.Notification;
import org.exadel.training.model.RegularLesson;
import org.exadel.training.model.Training;
import org.exadel.training.model.User;

import java.util.List;

public interface NotificationService {
    void addNotification(Notification notification);

    Notification getNotificationById(long notification);

    void removeNotification(Notification notification);
    
    List<Notification> getAllNotificationsByUser(long userId);

    void newTrainingEmailNotificationForAdmins(Training training);

    void trainingSchedulingNotifications(Training training);

    void regularLessonSchedulingNotifications(RegularLesson regularLesson);

    void newExternalCreationNotification(User external, String password);
}

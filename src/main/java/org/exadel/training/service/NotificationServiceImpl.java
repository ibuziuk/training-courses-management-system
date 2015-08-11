package org.exadel.training.service;

import org.exadel.training.dao.NotificationDAO;
import org.exadel.training.model.Notification;
import org.exadel.training.model.RegularLesson;
import org.exadel.training.model.Training;
import org.exadel.training.model.User;
import org.exadel.training.utils.RoleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationDAO notificationDAO;

    @Autowired
    private EmailNotifierService emailNotifierService;

    @Autowired
    private UserService userService;

    private String host;

    @Autowired
    private TrainingService trainingService;

    @Override
    @Transactional
    public void addNotification(long trainingId, long userId, int type) {
        List<User> admins = userService.getUsersByRole(RoleUtil.ADMIN_SMALL);
        Set<User> visitors = trainingService.getTrainingById(trainingId).getVisitors();
        switch (type) {
            case 1://add feedback
                for (User admin : admins) {
                    notificationDAO.addNotification(trainingId, admin.getUserId(), type);
                }
                break;
            case 2://approve training
                for (User admin : admins) {
                    notificationDAO.addNotification(trainingId, admin.getUserId(), type);
                }
                notificationDAO.addNotification(trainingId, userId, type);
                break;
            case 3://disapprove training
                for (User admin : admins) {
                    notificationDAO.addNotification(trainingId, admin.getUserId(), type);
                }
                notificationDAO.addNotification(trainingId, userId, type);
                break;
            case 4://for visitors about edit in training
                for (User visitor : visitors) {
                    //notificationDAO.addNotification(trainingId, visitor.getUserId(), type);
                }
                break;
            case 5:// create training
                for (User admin : admins) {
                    notificationDAO.addNotification(trainingId, admin.getUserId(), type);
                }
                break;
            case 6://remove user from waiting list and add to training list
                notificationDAO.addNotification(trainingId, userId, type);
                break;
            case 7://upload file
                for (User admin : admins) {
                    notificationDAO.addNotification(trainingId, admin.getUserId(), type);
                }
                break;
            case 8://edit training
                for (User admin : admins) {
                    notificationDAO.addNotification(trainingId, admin.getUserId(), type);
                }
                break;
            case 9://approve edit training
                //notificationDAO.addNotification(trainingId, userId, type);
                break;
            case 10://disapprove edit trainind
                notificationDAO.addNotification(trainingId, userId, type);
                break;
            case 11://for hour
                for (User visitor : visitors) {
                    notificationDAO.addNotification(trainingId, visitor.getUserId(), type);
                }
                notificationDAO.addNotification(trainingId, trainingService.getTrainingById(trainingId)
                        .getTrainer().getUserId(), type);
                break;
            case 12://for day
                for (User visitor : visitors) {
                    notificationDAO.addNotification(trainingId, visitor.getUserId(), type);
                }
                notificationDAO.addNotification(trainingId, trainingService.getTrainingById(trainingId)
                        .getTrainer().getUserId(), type);
                break;
        }

    }

    @Override
    @Transactional
    public void removeNotification(Notification notification) {
        notificationDAO.removeNotification(notification);
    }

    @Override
    @Transactional
    public List<Notification> getAllNotificationsByUser(long userId, long token) {
        return notificationDAO.getAllNotificationsByUser(userId, token);
    }

    @Override
    @Transactional
    public Notification getNotificationById(long notificationId) {
        return notificationDAO.getNotificationById(notificationId);
    }

    @Override
    public void newTrainingEmailNotificationForAdmins(Training training) {
        Context context = new Context();
        List<User> users = userService.getUsersByRole(RoleUtil.ADMIN_SMALL);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (User user : users) {
            context.setVariable("mailReceiver", user.getFirstName());
            context.setVariable("startText", "New training created: \"");
            context.setVariable("reference", host + "/training/" + training.getTrainingId());
            context.setVariable("nameTraining", training.getTitle());
            context.setVariable("middleText", "\" on ");
            context.setVariable("date", dateFormat.format(date) + ".");
            context.setVariable("endText", "Please approve or disapprove training as soon as possible.");
            emailNotifierService.sendEmailNotification(user.getEmail(), "New training", context);
        }
    }

    @Override
    public void trainingSchedulingNotifications(Training training) {
        User trainer = training.getTrainer();
        Context context = new Context();
        context.setVariable("mailReceiver", trainer.getFirstName());
        context.setVariable("startText", "You trainer on: \"");
        context.setVariable("middleText", "\" on ");
        context.setVariable("date", training.getDateAndTimeOnString());
        context.setVariable("reference", host + "/training/" + training.getTrainingId());
        context.setVariable("nameTraining", training.getTitle());
        emailNotifierService.sendEmailNotification(trainer.getEmail(), "Notification about the training", context);

        Set<User> currentList = training.getVisitors();
        context.setVariable("startText", "Do not forget to visit training");
        if (!currentList.isEmpty()) {
            for (User user : currentList) {
                context.setVariable("mailReceiver", user.getFirstName());
                emailNotifierService.sendEmailNotification(user.getEmail(), "Notification about the training", context);
            }
        }
    }

    @Override
    public void regularLessonSchedulingNotifications(RegularLesson regularLesson) {
        User trainer = regularLesson.getTraining().getTrainer();
        Context context = new Context();
        context.setVariable("mailReceiver", trainer.getFirstName());
        context.setVariable("startText", "You trainer on: \"");
        context.setVariable("middleText", "\" on ");
        context.setVariable("date", regularLesson.getDateAndTimeOnString());
        context.setVariable("reference", host + "/training/" + regularLesson.getTraining().getTrainingId());
        context.setVariable("nameTraining", regularLesson.getTraining().getTitle());
        emailNotifierService.sendEmailNotification(trainer.getEmail(), "Notification about the training", context);

        Set<User> currentList = regularLesson.getTraining().getVisitors();
        context.setVariable("startText", "Do not forget to visit training");
        if (!currentList.isEmpty()) {
            for (User user : currentList) {
                context.setVariable("mailReceiver", user.getFirstName());
                emailNotifierService.sendEmailNotification(user.getEmail(), "Notification about the training", context);
            }
        }
    }

    @Override
    public void newExternalCreationNotification(User external, String password) {
        Context context = new Context();
        context.setVariable("mailReceiver", external.getFirstName());
        context.setVariable("startText", "Your credentials for visiting Exadel Training System:");
        context.setVariable("login", "\nlogin: " + external.getLogin());
        context.setVariable("date", "\npassword: " + password);
        emailNotifierService.sendEmailNotification(external.getEmail(), "Your account has been created", context);

        List<User> users = userService.getUsersByRole(RoleUtil.ADMIN_SMALL);
        context.setVariable("startText", "Credentials of new coach: ");
        for (User user : users) {
            context.setVariable("mailReceiver", user.getFirstName());
            emailNotifierService.sendEmailNotification(user.getEmail(), "New user account has been created", context);
        }
    }

    public void setHost(String host) {
        this.host = host;
    }
}

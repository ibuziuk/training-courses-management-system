package org.exadel.training.service;

import org.exadel.training.model.RegularLesson;
import org.exadel.training.model.Training;
import org.exadel.training.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class NotificationService {
    @Autowired
    private EmailNotifierService emailNotifierService;

    @Autowired
    private UserService userService;

    public void newTrainingEmailNotificationForAdmins(Training training) {
        Context context = new Context();
        List<User> users = userService.getUsersByRole("admin");
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (User user : users) {
            context.setVariable("mailReceiver", user.getFirstName());
            context.setVariable("startText", "New training created: \"");
            context.setVariable("nameTraining", training.getTitle());
            context.setVariable("middleText", "\" on ");
            context.setVariable("date", dateFormat.format(date) + ".");
            context.setVariable("endText", "Please approve or disapprove training as soon as possible.");
            emailNotifierService.sendEmailNotification(user.getEmail(), "New training", context);
        }
    }

    public void trainingSchedulingNotifications(Training training) {
        User trainer = training.getTrainer();
        Context context = new Context();
        context.setVariable("startText", "You trainer on: \"");
        context.setVariable("mailReceiver", trainer.getFirstName());
        context.setVariable("middleText", "\" on ");
        context.setVariable("date", training.getDateAndTimeOnString());
        context.setVariable("nameTraining", training.getTitle());
        emailNotifierService.sendEmailNotification(trainer.getEmail(), "Notification about the training", context);

        Set<User> currentList = training.getVisitors();
        context.setVariable("startText", "Do not forget to visit training");
        if (!currentList.isEmpty()) {
            for (User user : currentList) {
                context.setVariable("mailReceiver", trainer.getFirstName());
                emailNotifierService.sendEmailNotification(user.getEmail(), "Notification about the training", context);
            }
        }
    }

    public void regularLessonSchedulingNotifications(RegularLesson regularLesson) {
        User trainer = regularLesson.getTraining().getTrainer();
        Context context = new Context();
        context.setVariable("startText", "You trainer on: \"");
        context.setVariable("mailReceiver", trainer.getFirstName());
        context.setVariable("middleText", "\" on ");
        context.setVariable("date", regularLesson.getDateAndTimeOnString());
        context.setVariable("nameTraining", regularLesson.getTraining().getTitle());
        emailNotifierService.sendEmailNotification(trainer.getEmail(), "Notification about the training", context);

        Set<User> currentList = regularLesson.getTraining().getVisitors();
        context.setVariable("startText", "Do not forget to visit training");
        if (!currentList.isEmpty()) {
            for (User user : currentList) {
                context.setVariable("mailReceiver", trainer.getFirstName());
                emailNotifierService.sendEmailNotification(user.getEmail(), "Notification about the training", context);
            }
        }
    }
}

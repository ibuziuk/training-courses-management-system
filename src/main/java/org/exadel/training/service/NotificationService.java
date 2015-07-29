package org.exadel.training.service;

import org.exadel.training.model.Training;
import org.exadel.training.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Set;

@Service
public class NotificationService {
    @Autowired
    private EmailNotifierService emailNotifierService;

    @Autowired
    private UserService userService;

    public void createNewTrainingEmailNotificationForAdmins(Training training) {
        Context context = new Context();
        List<User> users = userService.getUsersByRole("admin");
        for (User user : users) {
            context.setVariable("mailReceiver", user.getFirstName());
            context.setVariable("typeTraining", "New training created: ");
            context.setVariable("nameTraining", training.getTitle());
            emailNotifierService.sendEmailNotification(new String[]{user.getEmail()}, "New training", context);
        }
    }

    public void trainingSchedulingNotifications(Training training) {
        User trainer = training.getTrainer();
        Context context = new Context();
        context.setVariable("mailReceiver", trainer.getFirstName());
        context.setVariable("date", training.getDateAndTimeOnString());
        context.setVariable("nameTraining", training.getTitle());
        context.setVariable("typeTraining", "You are trainer on");
        emailNotifierService.sendEmailNotification(new String[]{trainer.getEmail()}, "Notification about the training", context);

        Set<User> currentList = training.getVisitors();
        context.setVariable("typeTraining", "Do not forget to visit training");
        for (User user : currentList) {
            emailNotifierService.sendEmailNotification(new String[]{user.getEmail()}, "Notification about the training", context);
        }
    }
}

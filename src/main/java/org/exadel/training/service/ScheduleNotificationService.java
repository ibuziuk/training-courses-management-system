package org.exadel.training.service;


import org.exadel.training.model.Training;
import org.exadel.training.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.thymeleaf.context.Context;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.Set;

@EnableScheduling
public class ScheduleNotificationService {
    private PriorityQueue<Training> notificationPerDay;
    private PriorityQueue<Training> notificationPerHour;

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private EmailNotifierService emailNotification;

    private void sendNotificationByEmail(Training training) {
        Set<User> currentList = training.getVisitors();
        Context context = new Context();
        User trainer = training.getTrainer();
        String userName = trainer.getFirstName() + " " + trainer.getLastName();
        emailNotification.sendEmailNotification(trainer.getEmail(), userName, training.getDateAndTimeOnString(),
                "trainer on", training.getTitle(), "Notification about the training", context);
        for (User user : currentList) {
            userName = user.getFirstName() + " " + user.getLastName();
            emailNotification.sendEmailNotification(user.getEmail(), userName, training.getDateAndTimeOnString(),
                    "have training", training.getTitle(), "Notification about the training", context);
        }
    }

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void post() {
        notificationPerDay = new PriorityQueue(trainingService.getComparatorByData());
        notificationPerDay.addAll(trainingService.getFutureTrainings());
        notificationPerHour = new PriorityQueue(trainingService.getComparatorByData());
        notificationPerHour.addAll(trainingService.getFutureTrainings());
    }

    public void addTrainingToSchedule(Training training) {
        notificationPerDay.add(training);
        notificationPerHour.add(training);
    }

    @Scheduled(cron = "* */1 * * * *")
    public synchronized void scheduleTask() {
        System.out.println("Id=" + Thread.currentThread().getId());
        long date = new Date().getTime();
        Timestamp currentTimePlusDay = new Timestamp(date + 86400000);
        Timestamp currentTimePlusHour = new Timestamp(date + 3600000);
        while (!notificationPerDay.isEmpty() && (notificationPerDay.peek().getDate().getTime() < currentTimePlusDay.getTime())) {
            Training training = notificationPerDay.poll();
            if (training.getDate().getTime() > date && training.getVisitors() != null) {
                sendNotificationByEmail(training);
            }
        }
        while (!notificationPerHour.isEmpty() && (notificationPerHour.peek().getDate().getTime() < currentTimePlusHour.getTime())) {
            Training training = notificationPerHour.poll();
            if (training.getDate().getTime() > date && training.getVisitors() != null) {
                sendNotificationByEmail(training);
            }
        }
    }
}
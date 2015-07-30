package org.exadel.training.service;


import org.exadel.training.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.Date;
import java.util.PriorityQueue;

@EnableScheduling
@EnableAsync
public class ScheduleNotificationService {
    private PriorityQueue<Training> notificationPerDay;
    private PriorityQueue<Training> notificationPerHour;

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private NotificationService notificationService;

    private void sendNotificationByEmail(Training training) {
        notificationService.trainingSchedulingNotifications(training);
    }

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void post() {
        notificationPerDay = new PriorityQueue(trainingService.getComparatorByData());
//        notificationPerDay.addAll(trainingService.getFutureTrainings());
        notificationPerHour = new PriorityQueue(trainingService.getComparatorByData());
//        notificationPerHour.addAll(trainingService.getFutureTrainings());
    }

    public void addTrainingToSchedule(Training training) {
        notificationPerDay.add(training);
        notificationPerHour.add(training);
    }

    @Async
    public void scheduleTask() {
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
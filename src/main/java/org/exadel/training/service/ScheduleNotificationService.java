package org.exadel.training.service;


import org.exadel.training.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    public synchronized void addTrainingToSchedule(Training training) {
        System.out.println("add " + Thread.currentThread().getId());
        notificationPerDay.add(training);
        notificationPerHour.add(training);
    }

    @Async
    public synchronized void scheduleTask() throws ParseException {
        System.out.println("schedule " + Thread.currentThread().getId());
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Europe/Minsk"));
        long date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()).getTime();

        Timestamp currentTimePlusDay = new Timestamp(date + 86400000);
        Timestamp currentTimePlusHour = new Timestamp(date + 3600000);
        while (!notificationPerDay.isEmpty() && (notificationPerDay.peek().getDate().getTime() < currentTimePlusDay.getTime())) {
            Training training = notificationPerDay.poll();
            if (training.getDate().getTime() > date) {
//                sendNotificationByEmail(training);
            }
        }
        while (!notificationPerHour.isEmpty() && (notificationPerHour.peek().getDate().getTime() < currentTimePlusHour.getTime())) {
            Training training = notificationPerHour.poll();
            if (training.getDate().getTime() > date) {
//                sendNotificationByEmail(training);
            }
        }
    }
}
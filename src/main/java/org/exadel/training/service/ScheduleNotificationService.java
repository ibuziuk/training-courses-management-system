package org.exadel.training.service;


import org.exadel.training.model.RegularLesson;
import org.exadel.training.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;

import static org.exadel.training.utils.TrainingUtil.getRegularLessonComparatorByData;
import static org.exadel.training.utils.TrainingUtil.getTrainingComparatorByData;

@EnableScheduling
@EnableAsync
public class ScheduleNotificationService {
    private PriorityQueue<Training> notificationTrainingPerDay;
    private PriorityQueue<Training> notificationTrainingPerHour;

    private PriorityQueue<RegularLesson> notificationRegularLessonPerDay;
    private PriorityQueue<RegularLesson> notificationRegularLessonPerHour;

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private RegularLessonService regularLessonService;

    @Autowired
    private NotificationService notificationService;

    private void sendNotificationTrainingByEmail(Training training) {
        notificationService.trainingSchedulingNotifications(training);
    }

    private void sendNotificationRegularLessonByEmail(RegularLesson regularLesson) {
        notificationService.regularLessonSchedulingNotifications(regularLesson);
    }

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void post() {
        notificationTrainingPerDay = new PriorityQueue(getTrainingComparatorByData());
        List<Training> futureTrainings = trainingService.getFutureTrainingsForScheduling();
        notificationTrainingPerDay.addAll(futureTrainings);
        notificationTrainingPerHour = new PriorityQueue(getTrainingComparatorByData());
        notificationTrainingPerHour.addAll(futureTrainings);

        notificationRegularLessonPerDay = new PriorityQueue(getRegularLessonComparatorByData());
        List<RegularLesson> futureRegularLessons = regularLessonService.getFutureRegularLessons();
        notificationRegularLessonPerDay.addAll(futureRegularLessons);
        notificationRegularLessonPerHour = new PriorityQueue(getRegularLessonComparatorByData());
        notificationRegularLessonPerHour.addAll(futureRegularLessons);
    }

    public synchronized void addTrainingToSchedule(Training training) {
        notificationTrainingPerDay.add(training);
        notificationTrainingPerHour.add(training);
    }

    public synchronized void addRegularLessonToSchedule(RegularLesson regularLesson) {
        notificationRegularLessonPerDay.add(regularLesson);
        notificationRegularLessonPerHour.add(regularLesson);
    }

    @Async
    public synchronized void scheduleTask() {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Europe/Minsk"));
        long date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()).getTime();

        Timestamp currentTimePlusDay = new Timestamp(date + 86400000);
        Timestamp currentTimePlusHour = new Timestamp(date + 3600000);

        while (!notificationTrainingPerDay.isEmpty() && (notificationTrainingPerDay.peek().getDate().getTime() < currentTimePlusDay.getTime())) {
            Training training = notificationTrainingPerDay.poll();
            if (training.getDate().getTime() > date) {
                sendNotificationTrainingByEmail(training);
            }
        }

        while (!notificationTrainingPerHour.isEmpty() && (notificationTrainingPerHour.peek().getDate().getTime() < currentTimePlusHour.getTime())) {
            Training training = notificationTrainingPerHour.poll();
            if (training.getDate().getTime() > date) {
                sendNotificationTrainingByEmail(training);
            }
        }

        while (!notificationRegularLessonPerDay.isEmpty() && (notificationRegularLessonPerDay.peek().getDate().getTime() < currentTimePlusDay.getTime())) {
            RegularLesson regularLesson = notificationRegularLessonPerDay.poll();
            if (regularLesson.getDate().getTime() > date) {
                sendNotificationRegularLessonByEmail(regularLesson);
            }
        }

        while (!notificationRegularLessonPerHour.isEmpty() && (notificationRegularLessonPerHour.peek().getDate().getTime() < currentTimePlusHour.getTime())) {
            RegularLesson regularLesson = notificationRegularLessonPerHour.poll();
            if (regularLesson.getDate().getTime() > date) {
                sendNotificationRegularLessonByEmail(regularLesson);
            }
        }
    }
}
package org.exadel.training.controller.rest;

import org.exadel.training.model.CustomUserDetails;
import org.exadel.training.model.Training;
import org.exadel.training.model.TrainingEdit;
import org.exadel.training.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@RestController
public class EditRestController {
    @Autowired
    private TrainingEditService trainingEditService;

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private RegularLessonService regularLessonService;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "/rest/training/{action}/{trainingId}", method = RequestMethod.POST)
    public Map<String, Object> editTraining(@RequestBody Map<String, Object> requestMap, @PathVariable("trainingId") long id, @PathVariable("action") String action) {
        Training training = trainingService.getTrainingById(id);
        TrainingEdit te = trainingEditService.getEditByTrainingIfExist(id);
        boolean flagNew = false;
        boolean flag = false;
        boolean regular = Boolean.parseBoolean(requestMap.get("regular").toString());
        if (te == null) {
            flagNew = true;
            te = new TrainingEdit();
        }
        if (requestMap.containsKey("title") && !requestMap.get("title").toString().equals(training.getTitle())) {
            flag = true;
            te.setTitle(requestMap.get("title").toString());
        }
        try {
            if (!regular && requestMap.containsKey("date") && requestMap.containsKey("times")) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                String dateString = requestMap.get("date").toString() + " " + requestMap.get("times");
                Timestamp date = new Timestamp(sdf.parse(dateString).getTime());
                if (!date.equals(training.getDate())) {
                    flag = true;
                    te.setDate(date);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!regular && requestMap.containsKey("times")) {
            SimpleDateFormat hourDateFormater = new SimpleDateFormat("HH:mm");
            try {
                java.util.Date parsedTime = hourDateFormater.parse(requestMap.get("times").toString());
                String time = hourDateFormater.format(parsedTime);
                Calendar calendarTime = GregorianCalendar.getInstance();
                calendarTime.setTime(parsedTime);
                calendarTime.set(Calendar.MINUTE, calendarTime.get(Calendar.MINUTE) + Integer.parseInt(requestMap.get("duration").toString()));
                time += "-" + hourDateFormater.format(calendarTime.getTime());
                if (!time.equals(training.getTime())) {
                    flag = true;
                    te.setTime(time);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            SimpleDateFormat hourDateFormater = new SimpleDateFormat("HH:mm");
            String timeFromJson = requestMap.get("times").toString();
            List<String> timesArray = new ArrayList<>();
            StringTokenizer stringTokenizer = new StringTokenizer(timeFromJson, " ");
            while (stringTokenizer.hasMoreElements()) {
                timesArray.add((String) stringTokenizer.nextElement());
            }
            String time = "";
            for (String timeString : timesArray) {
                try {
                    java.util.Date parsedTime = hourDateFormater.parse(timeString);
                    time += hourDateFormater.format(parsedTime);
                    Calendar calendarTime = GregorianCalendar.getInstance();
                    calendarTime.setTime(parsedTime);
                    calendarTime.set(Calendar.MINUTE, calendarTime.get(Calendar.MINUTE) + Integer.parseInt(requestMap.get("duration").toString()));
                    time += "-" + hourDateFormater.format(calendarTime.getTime()) + " ";
                    if (!time.equals(training.getTime())) {
                        flag = true;
                        te.setTime(time);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        if (!regular && requestMap.containsKey("rooms") && !requestMap.get("rooms").toString().equals(training.getLocation())) {
            flag = true;
            te.setLocation(requestMap.get("rooms").toString());
        }
        if (requestMap.containsKey("duration") && Integer.parseInt(requestMap.get("duration").toString()) != training.getDuration()) {
            flag = true;
            te.setDuration(Integer.parseInt(requestMap.get("duration").toString()));
        }
        if (requestMap.containsKey("description") && !requestMap.get("description").toString().equals(training.getDescription())) {
            flag = true;
            te.setDescription(requestMap.get("description").toString());
        }
        if (requestMap.containsKey("language") && !requestMap.get("language").toString().equals(training.getLanguage().getValue())) {
            flag = true;
            te.setLanguage(languageService.getLanguageByValue(requestMap.get("language").toString()));
        }
        if (regular && requestMap.containsKey("days") && !requestMap.get("days").toString().equals(training.getDays())) {
            flag = true;
            te.setDays(requestMap.get("days").toString());
        }
        try {
            if (regular && requestMap.containsKey("start")) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                Date start = new java.sql.Date(sdf.parse(requestMap.get("start").toString()).getTime());
                if (!start.equals(training.getStart())) {
                    flag = true;
                    te.setStart(start);
                }

            }
            if (regular && requestMap.containsKey("end")) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                Date end = new java.sql.Date(sdf.parse(requestMap.get("end").toString()).getTime());
                if (!end.equals(training.getEnd())) {
                    flag = true;
                    te.setEnd(end);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        te.setTraining(training);
        if (flag) {
            training.setApproved(null);
            if (flagNew) {
                trainingEditService.addEdit(te);
            } else {
                trainingEditService.updateEdit(te);
            }
        }
        training.setIsEditing(true);
        trainingService.updateTraining(training);
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userService.getUserById(userDetails.getId()).getRoleForView().equals("Administrator") || !action.equals("edit")) {
            approveEdit(action, id);
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("id", id);
        notificationService.addNotification(training.getTrainingId(), 0L, 8);

        return map;
    }

    private Map<String, Object> approveEdit(String approve, long id) {
        TrainingEdit te = trainingEditService.getEditByTrainingIfExist(id);
        Training training = trainingService.getTrainingById(id);
        Map<String, Object> map = new HashMap<>();
        if (te != null) {
            if (approve.equals("approve")) {
                te.setIsApproved(true);
                if (te.getDate() != null) {
                    training.setDate(te.getDate());
                }
                if (te.getDays() != null) {
                    training.setDays(te.getDays());
                }
                if (te.getDescription() != null) {
                    training.setDescription(te.getDescription());
                }
                if (te.getDuration() != null) {
                    training.setDuration(te.getDuration());
                }
                if (te.getEnd() != null) {
                    training.setEnd(te.getEnd());
                }
                if (te.getLocation() != null) {
                    training.setLocation(te.getLocation());
                }
                if (te.getMaxVisitorsCount() != null) {
                    training.setMaxVisitorsCount(te.getMaxVisitorsCount());
                }
                if (te.getStart() != null) {
                    training.setStart(te.getStart());
                }
                if (te.getTime() != null) {
                    training.setTime(te.getTime());
                }
                if (te.getTitle() != null) {
                    training.setTitle(te.getTitle());
                }
                if (te.getLanguage() != null) {
                    training.setLanguage(te.getLanguage());
                }
                training.setApproved(true);
                training.setIsEditing(false);
                trainingService.updateTraining(training);
                notificationService.addNotification(id, training.getTrainer().getUserId(), 9);
                notificationService.addNotification(id, training.getTrainer().getUserId(), 4);
            } else {
                te.setIsApproved(false);
                notificationService.addNotification(id, trainingService.getTrainingById(id).getTrainer().getUserId(), 10);
            }
            trainingEditService.updateEdit(te);
            map.put("id", id);
        } else {
            if (approve.equals("approve")) {
                training.setApproved(true);
            } else {
                training.setApproved(false);
            }
            training.setIsEditing(false);
            trainingService.updateTraining(training);
        }
        return map;
    }

    @RequestMapping(value = "/rest/training/approve/{trainingId}", method = RequestMethod.GET)
    public Map<String, Object> editionTraining(@PathVariable("trainingId") long id) {
        Map<String, Object> map = new HashMap<>(2);
        Map<String, Object> old = new HashMap<>();
        Training training = trainingService.getTrainingById(id);
        old.put("training", training);
        List<TrainingEdit> edits;
        if (training.getContinuous()) {
            List<Training> continuous = trainingService.getContinuousTrainings(id);
            old.put("parts", continuous);
            edits = new ArrayList<>();
            edits.addAll(continuous.stream().map(tr -> trainingEditService.getEditByTrainingIfExist(tr.getTrainingId())).collect(Collectors.toList()));
            map.put("edit", edits);
        } else {
            map.put("edit", trainingEditService.getEditByTrainingIfExist(id));
        }
        if (training.isRegular()) {
            old.put("closedLessons", regularLessonService.getSomeFutureLessonsByTraining(id, 5));
        }
        map.put("old", old);
        return map;
    }
}

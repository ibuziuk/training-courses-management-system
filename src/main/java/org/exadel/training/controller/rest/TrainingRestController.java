package org.exadel.training.controller.rest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.exadel.training.model.*;
import org.exadel.training.service.*;
import org.exadel.training.utils.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class TrainingRestController {
    @Autowired
    private TrainingService trainingService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    @Autowired
    private AudienceService audienceService;

    @Autowired
    private RegularLessonService regularLessonService;

    @Autowired
    private TrainingFeedbackService trainingFeedbackService;

    @Autowired
    private WaitingListService waitingListService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ScheduleNotificationService scheduleNotificationService;

    @RequestMapping(value = "/rest/training", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String createTraining(HttpEntity<String> httpEntity) {
        String jsonString = httpEntity.getBody();
        JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();

        Training training = new Training();
        if (json.get("title") != null) {
            training.setTitle(json.get("title").getAsString());
        }

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User trainer = userService.getUserById(userDetails.getId());
        training.setTrainer(trainer);

        if (json.get("visitors") != null) {
            training.setMaxVisitorsCount(json.get("visitors").getAsInt());
        }
        if (json.get("duration") != null) {
            training.setDuration(json.get("duration").getAsInt());
        }
        if (json.get("description") != null) {
            training.setDescription(json.get("description").getAsString());
        }
        if (json.get("regular") != null) {
            training.setRegular(json.get("regular").getAsBoolean());
        }
        if (json.get("type") != null) {
            training.setExternalType(json.get("type").getAsBoolean());
        }
        if (json.get("continuous") != null) {
            training.setContinuous(json.get("continuous").getAsBoolean());
        }
        if (json.get("approved") != null) {
            training.setApproved(json.get("approved").getAsBoolean());
        } else {
            training.setApproved(false);
        }
        Language language = null;
        String languageValue = json.get("language").getAsString();
        if (languageValue != null) {
            language = languageService.getLanguageByValue(languageValue);
        }
        training.setLanguage(language);

        Set<Audience> audiences = training.getAudiences();
        Audience audience;
        JsonArray jsonArray = json.get("audience").getAsJsonArray();
        for (JsonElement audienceValue : jsonArray) {
            audience = audienceService.getAudienceByValue(audienceValue.getAsString());
            if (audience != null) {
                audiences.add(audience);
            }
        }
        training.setAudiences(audiences);

        Set<Tag> tags = training.getTags();
        Tag tag;
        jsonArray = json.get("tags").getAsJsonArray();
        for (JsonElement tagValue : jsonArray) {
            tag = tagService.getTagByName(tagValue.getAsString());
            if (tag != null) {
                tags.add(tag);
            }
        }

        if (training.isRegular()) {
            if (json.get("days") != null) {
                training.setDays(json.get("days").getAsString());
            }
            String startDateString = null;
            if (json.get("date") != null) {
                startDateString = json.get("date").getAsString();
            }
            String endDateString = null;
            if (json.get("end") != null) {
                endDateString = json.get("end").getAsString();
            }
            try {
                int minimumDayNumber = 7;
                StringTokenizer stringTokenizer = new StringTokenizer(training.getDays(), " ");
                List<Integer> days = new ArrayList<Integer>();
                while (stringTokenizer.hasMoreElements()) {
                    days.add(Integer.parseInt(((String) stringTokenizer.nextElement())));
                    if (minimumDayNumber > days.get(days.size() - 1)) {
                        minimumDayNumber = days.get(days.size() - 1);
                    }
                }

                Calendar startCalendar = GregorianCalendar.getInstance();
                Calendar iterationCalendar = GregorianCalendar.getInstance();
                Calendar endCalendar = GregorianCalendar.getInstance();
                Calendar durationTime = GregorianCalendar.getInstance();

                SimpleDateFormat dateFormater = new SimpleDateFormat("dd.MM.yyyy");
                SimpleDateFormat hourDateFormater = new SimpleDateFormat("HH:mm");
                SimpleDateFormat fullDateFormater = new SimpleDateFormat("dd.MM.yyyy HH:mm");

                startCalendar.setTime(dateFormater.parse(startDateString));

                training.setStart(new java.sql.Date(startCalendar.getTime().getTime()));

                if (endDateString == null) {
                    endCalendar.set(startCalendar.get(Calendar.YEAR) + 1, startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DATE));
                } else {
                    endCalendar.setTime(dateFormater.parse(endDateString));
                }
                training.setEnd(new java.sql.Date(endCalendar.getTime().getTime()));

                String timeForTrainingTable = null;
                String timeWithDuration = null;
                String parsedTimeForTrainingTable = null;
                if (json.get("times") != null) {
                    timeForTrainingTable = "";
                    for (int i = 0; i < days.size(); i++) {
                        parsedTimeForTrainingTable = json.get("times").getAsJsonArray().get(i).getAsString();
                        durationTime.setTime(hourDateFormater.parse(parsedTimeForTrainingTable));
                        durationTime.set(Calendar.MINUTE, durationTime.get(Calendar.MINUTE) + training.getDuration());
                        timeWithDuration = hourDateFormater.format(durationTime.getTime());
                        timeForTrainingTable += parsedTimeForTrainingTable + "-" + timeWithDuration + " ";
                    }
                }
                training.setTime(timeForTrainingTable);

                trainingService.addTraining(training);

                for (int i = 0; i < days.size(); i++) {
                    if (days.get(i) < (startCalendar.get(Calendar.DAY_OF_WEEK) + 5) % 7) {
                        iterationCalendar.set(startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DATE) + days.get(i) - (startCalendar.get(Calendar.DAY_OF_WEEK) + 5) % 7 + 7);
                    } else {
                        iterationCalendar.set(startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DATE) + days.get(i) - (startCalendar.get(Calendar.DAY_OF_WEEK) + 5) % 7);
                    }
                    String time = null;
                    if (json.get("times") != null) {
                        time = json.get("times").getAsJsonArray().get(i).getAsString();
                    }
                    int location = -1;
                    if (json.get("rooms") != null) {
                        if (json.get("rooms").getAsJsonArray().size() != 0) {
                            location = json.get("rooms").getAsJsonArray().get(i).getAsInt();
                        }
                    }
                    while (iterationCalendar.getTimeInMillis() <= endCalendar.getTimeInMillis()) {
                        RegularLesson regularLesson = new RegularLesson();

                        if (location != -1) {
                            regularLesson.setLocation(location);
                        }
                        Date fullDate = fullDateFormater.parse(dateFormater.format(iterationCalendar.getTime()) + " " + time);
                        regularLesson.setDate(new java.sql.Timestamp(fullDate.getTime()));

                        Date parsedTime = hourDateFormater.parse(time);
                        Calendar calendarTime = GregorianCalendar.getInstance();
                        calendarTime.setTime(parsedTime);
                        calendarTime.set(calendarTime.get(Calendar.YEAR), calendarTime.get(Calendar.MONTH), calendarTime.get(Calendar.DATE), calendarTime.get(Calendar.HOUR_OF_DAY), calendarTime.get(Calendar.MINUTE) + training.getDuration());

                        regularLesson.setTime(hourDateFormater.format(parsedTime) + " - " + hourDateFormater.format(calendarTime.getTime()));
                        regularLesson.setTraining(training);
                        regularLessonService.addRegularLesson(regularLesson);

                        scheduleNotificationService.addRegularLessonToSchedule(regularLesson);

                        iterationCalendar.set(iterationCalendar.get(Calendar.YEAR), iterationCalendar.get(Calendar.MONTH), iterationCalendar.get(Calendar.DATE) + 7);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }

        } else {
            if (json.get("rooms") != null) {
                if (json.get("rooms").getAsJsonArray().size() != 0) {
                    training.setLocation(json.get("rooms").getAsJsonArray().get(0).getAsInt());
                }
            }

            String dateString = null;
            if (json.get("date") != null) {
                dateString = json.get("date").getAsString();
            }

            String time = null;
            if (json.get("times") != null) {
                time = json.get("times").getAsJsonArray().get(0).getAsString();
            }
            dateString += " " + time;

            try {
                SimpleDateFormat fullDateFormater = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                SimpleDateFormat hourDateFormater = new SimpleDateFormat("HH:mm");

                Date parsedDate = fullDateFormater.parse(dateString);
                Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                training.setDate(timestamp);

                parsedDate = hourDateFormater.parse(time);
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTime(parsedDate);
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE) + training.getDuration());
                time = hourDateFormater.format(parsedDate) + " - " + hourDateFormater.format(calendar.getTime());
                training.setTime(time);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            trainingService.addTraining(training);

            scheduleNotificationService.addTrainingToSchedule(training);
        }

        final Training finalTraining = training;
        new Thread(() ->
                notificationService.newTrainingEmailNotificationForAdmins(finalTraining)
        ).start();

        return "{\"id\":\"" + training.getTrainingId() + "\"}";

    }

    @RequestMapping(value = "/rest/training/{trainingId}", method = RequestMethod.GET)
    public Map<String, Object> getTraining(@PathVariable("trainingId") long trainingId) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> map = new HashMap<>();
        Training training = trainingService.getTrainingById(trainingId);
        User user = userService.getUserById(userDetails.getId());
        map.put("training", training);
        map.put("rating", trainingFeedbackService.getAverageRatingByTrainingID(trainingId));
        if (trainingService.containsVisitor(trainingId, userDetails.getId())) {
            map.put("register", 0);
        } else if (waitingListService.checkingExist(trainingId, userDetails.getId())) {
            map.put("register", 1);
        } else if (training.getTrainer().equals(user)) {
            map.put("register", 2);
        } else {
            map.put("register", 3);
        }
        map.put("feedbacks", training.getTrainingFeedbacks());
        map.put("vote", trainingFeedbackService.containsUserByTraining(trainingId, userDetails.getId()));
        map.put("isAdmin", !user.getRoleForView().equals("User"));
        if (training.getContinuous()) {
            map.put("parts", trainingService.getContinuousTrainings(trainingId));
        }
        return map;
    }

    @RequestMapping(value = "/rest/register/training/{trainingId}")
    public Map<String, Object> registerForTraining(@PathVariable("trainingId") long trainingId) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> map = new HashMap<>(1);
        map.put("result", trainingService.registerForTraining(trainingId, userDetails.getId()));
        return map;
    }

    @RequestMapping(value = "/rest/unregister/training/{trainingId}")
    public Map<String, Object> unregisterFromTraining(@PathVariable("trainingId") long trainingId) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> map = new HashMap<>(1);
        map.put("result", trainingService.removeVisitor(trainingId, userDetails.getId()));
        return map;
    }

    @RequestMapping(value = "/rest/training/{come}", method = RequestMethod.GET,
            params = {"pageNum", "pageSize", "sorting", "order"})
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getFutureTrainings(
            @PathVariable("come") String come,
            @RequestParam(value = "pageNum") int pageNum,
            @RequestParam(value = "pageSize") int pageSize,
            @RequestParam(value = "sorting") String sorting,
            @RequestParam(value = "order") String order) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserById(userDetails.getId());
        boolean flag = !user.getRoleForView().equals("User");
        Map<String, Object> map = new HashMap<>(2);
        map.put("list", trainingService.getSomeTrainingOrderBy(come, pageNum, pageSize, sorting, order, flag));
        map.put("size", trainingService.getComeTrainings(come, flag).size());
        return map;
    }


    @RequestMapping(value = "/rest/training/search", method = RequestMethod.GET,
            params = {"type", "value"})
    @ResponseStatus(HttpStatus.OK)
    public List<Training> searching(
            @RequestParam("value") String value,
            @RequestParam("type") String type) {
        switch (type) {
            case "title":
                return trainingService.searchTrainingByTitle(value);
            case "date":
//                return trainingService.searchTrainingsByDate(new Timestamp(Long.parseLong(value)));
//                do not work now
                return null;
            case "time":
                return trainingService.searchTrainingsByTime(value);
            case "location":
                return trainingService.searchTrainingsByLocation(Integer.parseInt(value));
            case "trainerName":
                String[] str = value.split(" ");
                return trainingService.searchTrainingsByTrainerName(str[0], str[1]);
            default:
                return new ArrayList<>(0);
        }
    }
}
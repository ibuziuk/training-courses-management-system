package org.exadel.training.controller.rest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.exadel.training.model.*;
import org.exadel.training.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.exadel.training.utils.RoleUtil.ROLE_ADMIN;

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

    @Autowired
    private UploadFileService uploadFileService;

    @RequestMapping(value = "/rest/training", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String createTraining(HttpEntity<String> httpEntity) {
        String jsonString = httpEntity.getBody();
        JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();

        Training training = new Training();
        if (json.get("title") != null) {
            training.setTitle(json.get("title").getAsString());
        }
        training.setIsEditing(false);
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
        if (userService.getUserById(userDetails.getId()).getRoleForView().equals("Administrator")) {
            training.setApproved(true);
        } else {
            training.setApproved(null);
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
            if (json.get("rooms") != null) {
                training.setLocation(json.get("rooms").getAsString());
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
                        timeForTrainingTable += hourDateFormater.format(durationTime.getTime());
                        durationTime.set(Calendar.MINUTE, durationTime.get(Calendar.MINUTE) + training.getDuration());
                        timeWithDuration = hourDateFormater.format(durationTime.getTime());
                        timeForTrainingTable += "-" + timeWithDuration + " ";
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
                    String location = null;
                    if (json.get("rooms") != null) {
                        if (json.get("rooms").getAsJsonArray().size() != 0) {
                            location = json.get("rooms").getAsJsonArray().get(i).getAsString();
                        }
                    }
                    while (iterationCalendar.getTimeInMillis() <= endCalendar.getTimeInMillis()) {
                        RegularLesson regularLesson = new RegularLesson();

                        if (location != null) {
                            regularLesson.setLocation(location);
                        }
                        Date fullDate = fullDateFormater.parse(dateFormater.format(iterationCalendar.getTime()) + " " + time);
                        regularLesson.setDate(new java.sql.Timestamp(fullDate.getTime()));

                        Date parsedTime = hourDateFormater.parse(time);
                        Calendar calendarTime = GregorianCalendar.getInstance();
                        calendarTime.setTime(parsedTime);
                        calendarTime.set(calendarTime.get(Calendar.YEAR), calendarTime.get(Calendar.MONTH), calendarTime.get(Calendar.DATE), calendarTime.get(Calendar.HOUR_OF_DAY), calendarTime.get(Calendar.MINUTE) + training.getDuration());

                        regularLesson.setTime(hourDateFormater.format(parsedTime) + "-" + hourDateFormater.format(calendarTime.getTime()));
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
                    training.setLocation(json.get("rooms").getAsJsonArray().get(0).getAsString());
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
                time = hourDateFormater.format(parsedDate) + "-" + hourDateFormater.format(calendar.getTime());
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
            params = {"person", "pageNumber", "pageSize", "sorting", "order"})
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getComeTrainings(
            @PathVariable("come") String come,
            @RequestParam(value = "person") String person,
            @RequestParam(value = "pageNumber") int pageNum,
            @RequestParam(value = "pageSize") int pageSize,
            @RequestParam(value = "sorting") String sorting,
            @RequestParam(value = "order") String order) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserById(userDetails.getId());
        boolean flag = !user.getRoleForView().equals("User");
        if (!person.equals("all")) {
            person = userDetails.getId() + "";
        }
        return trainingService.getSomeTrainingOrderBy(person, come, pageNum, pageSize, sorting, order, flag);
    }

    @RequestMapping(value = "/rest/training/hot", method = RequestMethod.GET,
            params = {"person", "pageNumber", "pageSize", "sorting", "order"})
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getHotTrainings(
            @RequestParam(value = "person") String person,
            @RequestParam(value = "pageNumber") int pageNum,
            @RequestParam(value = "pageSize") int pageSize,
            @RequestParam(value = "sorting") String sorting,
            @RequestParam(value = "order") String order) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserById(userDetails.getId());
        boolean flag = !user.getRoleForView().equals("User");

        Map<String, Object> result = trainingService.getSomeTrainingOrderBy(person, "future", pageNum, pageSize, sorting, order, flag);
        List<Training> trainings = (List<Training>) result.get("list");
        List<Training> hotTrainings = new LinkedList<>();
        int i = 0;
        for (Training training : trainings) {
            if (training.getMaxVisitorsCount() > training.getVisitors().size() && i < 10) {
                hotTrainings.add(training);
                i++;
            }
        }
        result.put("list", hotTrainings);
        return result;
    }

    @RequestMapping(value = "/rest/training/new", method = RequestMethod.GET,
            params = {"person", "pageNumber", "pageSize", "sorting", "order"})
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getNewTrainings(
            @RequestParam(value = "person") String person,
            @RequestParam(value = "pageNumber") int pageNum,
            @RequestParam(value = "pageSize") int pageSize,
            @RequestParam(value = "sorting") String sorting,
            @RequestParam(value = "order") String order) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserById(userDetails.getId());
        boolean flag = !user.getRoleForView().equals("User");

        Map<String, Object> result = trainingService.getSomeTrainingOrderBy(person, "future", pageNum, pageSize, sorting, order, flag);
        List<Training> trainings = (List<Training>) result.get("list");
        List<Training> newTrainings = new LinkedList<>();
        int i = 0;
        for (Training training : trainings) {
            if (training.getMaxVisitorsCount() > training.getVisitors().size() && i < 10) {
                newTrainings.add(training);
                i++;
            }
        }
        Collections.sort(newTrainings, (o1, o2) -> {
            if (o1.getTrainingId() > o2.getTrainingId()) {
                return -1;
            } else {
                return 1;
            }
        });
        result.put("list", newTrainings);
        return result;
    }


    @RequestMapping(value = "/rest/training/search", method = RequestMethod.GET,
            params = {"pageNumber", "pageSize", "searchType", "value"})
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> searching(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam(value = "person") String person,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("searchType") String searchType,
            @RequestParam("value") String value,
            @PathVariable("come") String come) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserById(userDetails.getId());
        boolean flag = !user.getRoleForView().equals("User");
        if (!person.equals("all")) {
            person = userDetails.getId() + "";
        }
        String val = null;
        try {
            val = URLDecoder.decode(value, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trainingService.searchTrainings(person, come, flag, pageNumber, pageSize, searchType, val);
    }

    @RequestMapping(value = "/rest/training/recommendations", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getRecommendations() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> map = new HashMap<>();
        map.put("list", trainingService.getRecommendationsByUser(userDetails.getId()));
        return map;
    }

    @RequestMapping(value = "/rest/training/userTags", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getUserTags() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> map = new HashMap<>();
        map.put("tags", trainingService.getUserTags(userDetails.getId()).keySet().toArray());
        return map;
    }

    @RequestMapping(value = "/training/{trainingId}/uploadfile", method = RequestMethod.POST)
    public UploadFile uploadFileHandler(@RequestParam("file") MultipartFile file, @PathVariable("trainingId") long trainingId) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long uploadId = -1;
        if (userService.getUserById(userDetails.getId()).getRoleForView().equals("Administrator") ||
                trainingService.getTrainingById(trainingId).getTrainer().getUserId() == userDetails.getId()) {
            if (trainingService.getTrainingById(trainingId) != null) {
                uploadId = uploadFileService.addUploadFile(trainingId, file);
            }
        }
        if (uploadId != -1)
            return uploadFileService.getUploadFile(uploadId);
        return null;
    }

    @RequestMapping(value = "/rest/downloadfile/{fileId}", method = RequestMethod.GET)
    public void downloadFileHandler(HttpServletResponse response, @PathVariable("fileId") long fileId) {
        uploadFileService.getUploadFile(fileId, response);
    }

    @RequestMapping(value = "/rest/training/{trainingId}/files", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<UploadFile> getFilesNameByTraining(@PathVariable("trainingId") long trainingId) {
        return uploadFileService.getUploadFilesByTraining(trainingId);
    }

    @RequestMapping(value = "/rest/deletefile/{fileId}", method = RequestMethod.DELETE)
    public void deleteFileHandler(@PathVariable("fileId") long fileId) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = trainingService.getTrainingById(uploadFileService.getUploadFile(fileId).getTraining().getTrainingId()).getTrainer().getUserId();
        if (userDetails.hasRole(ROLE_ADMIN) || userId == userDetails.getId()) {
            uploadFileService.removeUploadFile(uploadFileService.getUploadFile(fileId));
        }
    }
}
package org.exadel.training.controller.rest;

import org.exadel.training.model.Training;
import org.exadel.training.model.TrainingEdit;
import org.exadel.training.service.LanguageService;
import org.exadel.training.service.TrainingEditService;
import org.exadel.training.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@RestController
public class EditRestController {
    @Autowired
    private TrainingEditService trainingEditService;

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private LanguageService languageService;

    @RequestMapping(value = "/rest/training/edit/{editId}", method = RequestMethod.POST)
    public Map<String, Object> editTraining(@RequestBody Map<String, Object> requestMap, @PathVariable("editId") long id) {
        Training training = trainingService.getTrainingById(id);
        TrainingEdit trainingEdit = trainingEditService.getEditByTrainingIfExist(id);
        boolean flagNew = false;
        boolean flag = false;
        boolean regular = Boolean.parseBoolean(requestMap.get("regular").toString());
        if (trainingEdit == null) {
            flagNew = true;
            trainingEdit = new TrainingEdit();
        }
        if (requestMap.containsKey("title") && !requestMap.get("title").toString().equals(training.getTitle())) {
            flag = true;
            trainingEdit.setTitle(requestMap.get("title").toString());
        }
        try {
            if (!regular && requestMap.containsKey("date") && requestMap.containsKey("times")) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                String dateString = requestMap.get("date").toString() + " " + requestMap.get("times");
                Timestamp date = new Timestamp(sdf.parse(dateString).getTime());
                if (!date.equals(training.getDate())) {
                    flag = true;
                    trainingEdit.setDate(date);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (requestMap.containsKey("maxVisitorsCount") && Integer.parseInt(requestMap.get("maxVisitorsCount").toString()) != training.getMaxVisitorsCount()) {
            flag = true;
            trainingEdit.setMaxVisitorsCount(Integer.parseInt(requestMap.get("maxVisitorsCount").toString()));
        }
        if (requestMap.containsKey("times") && !requestMap.get("times").toString().equals(training.getTime())) {
            flag = true;
            trainingEdit.setTime(requestMap.get("times").toString());
        }
        if (!regular && requestMap.containsKey("rooms") && !requestMap.get("rooms").toString().equals(training.getLocation())) {
            flag = true;
            trainingEdit.setLocation(requestMap.get("rooms").toString());
        }
        if (requestMap.containsKey("duration") && Integer.parseInt(requestMap.get("duration").toString()) != training.getDuration()) {
            flag = true;
            trainingEdit.setDuration(Integer.parseInt(requestMap.get("duration").toString()));
        }
        if (requestMap.containsKey("description") && !requestMap.get("description").toString().equals(training.getDescription())) {
            flag = true;
            trainingEdit.setDescription(requestMap.get("description").toString());
        }
        if (requestMap.containsKey("language") && !requestMap.get("language").toString().equals(training.getLanguage().getValue())) {
            flag = true;
            trainingEdit.setLanguage(languageService.getLanguageByValue(requestMap.get("language").toString()));
        }
        if (regular && requestMap.containsKey("days") && !requestMap.get("days").toString().equals(training.getDays())) {
            flag = true;
            trainingEdit.setDays(requestMap.get("days").toString());
        }
        try {
            if (regular && requestMap.containsKey("start")) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                Date start = new java.sql.Date(sdf.parse(requestMap.get("start").toString()).getTime());
                if (!start.equals(training.getStart())) {
                    flag = true;
                    trainingEdit.setStart(start);
                }

            }
            if (regular && requestMap.containsKey("end")) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                Date end = new java.sql.Date(sdf.parse(requestMap.get("end").toString()).getTime());
                if (!end.equals(training.getEnd())) {
                    flag = true;
                    trainingEdit.setEnd(end);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        trainingEdit.setTraining(training);
        if (flag) {
            if (flagNew) {
                trainingEditService.addEdit(trainingEdit);
            } else {
                trainingEditService.updateEdit(trainingEdit);
            }
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("id", id);
        return map;
    }

    @RequestMapping(value = "/rest/training/edit/{editId}/{approve}")
    public Map<String, Object> approveEdit(@PathVariable("approve") String approve, @PathVariable("editId") long id) {
        TrainingEdit trainingEdit = trainingEditService.getEditByTrainingIfExist(id);
        Map<String, Object> map = new HashMap<>();
        if (trainingEdit != null) {
            if (approve.equals("approve")) {
                trainingEdit.setIsApproved(true);
                Training training = trainingService.getTrainingById(id);
                if (trainingEdit.getDate() != null) {
                    training.setDate(trainingEdit.getDate());
                }
                if (trainingEdit.getDays() != null) {
                    training.setDays(trainingEdit.getDays());
                }
                if (trainingEdit.getDescription() != null) {
                    training.setDescription(trainingEdit.getDescription());
                }
                if (trainingEdit.getDuration() != null) {
                    training.setDuration(trainingEdit.getDuration());
                }
                if (trainingEdit.getEnd() != null) {
                    training.setEnd(trainingEdit.getEnd());
                }
                if (trainingEdit.getLocation() != null) {
                    training.setLocation(trainingEdit.getLocation());
                }
                if (trainingEdit.getMaxVisitorsCount() != null) {
                    training.setMaxVisitorsCount(trainingEdit.getMaxVisitorsCount());
                }
                if (trainingEdit.getStart() != null) {
                    training.setStart(trainingEdit.getStart());
                }
                if (trainingEdit.getTime() != null) {
                    training.setTime(trainingEdit.getTime());
                }
                if (trainingEdit.getTitle() != null) {
                    training.setTitle(trainingEdit.getTitle());
                }
                if (trainingEdit.getLanguage() != null) {
                    training.setLanguage(trainingEdit.getLanguage());
                }
            } else {
                trainingEdit.setIsApproved(false);
            }
            trainingEditService.updateEdit(trainingEdit);
            map.put("id", id);
        }
        return map;
    }

    @RequestMapping(value = "/rest/training/approve/{trainingId}")
    public Map<String, Object> editionTraining(@PathVariable("trainingId") long id) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("old", trainingService.getTrainingById(id));
        map.put("edit", trainingEditService.getEditByTrainingIfExist(id));
        return map;
    }
}

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
        if (requestMap.containsKey("maxVisitorsCount") && Integer.parseInt(requestMap.get("maxVisitorsCount").toString()) != training.getMaxVisitorsCount()) {
            flag = true;
            te.setMaxVisitorsCount(Integer.parseInt(requestMap.get("maxVisitorsCount").toString()));
        }
        if (requestMap.containsKey("times") && !requestMap.get("times").toString().equals(training.getTime())) {
            flag = true;
            te.setTime(requestMap.get("times").toString());
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
            if (flagNew) {
                trainingEditService.addEdit(te);
            } else {
                trainingEditService.updateEdit(te);
            }
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("id", id);
        return map;
    }

    @RequestMapping(value = "/rest/training/edit/{editId}/{approve}")
    public Map<String, Object> approveEdit(@PathVariable("approve") String approve, @PathVariable("editId") long id) {
        TrainingEdit te = trainingEditService.getEditByTrainingIfExist(id);
        Map<String, Object> map = new HashMap<>();
        if (te != null) {
            if (approve.equals("approve")) {
                te.setIsApproved(true);
                Training training = trainingService.getTrainingById(id);
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
            } else {
                te.setIsApproved(false);
            }
            trainingEditService.updateEdit(te);
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

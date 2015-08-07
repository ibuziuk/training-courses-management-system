package org.exadel.training.controller.rest;

import org.exadel.training.model.Training;
import org.exadel.training.model.TrainingEdit;
import org.exadel.training.service.LanguageService;
import org.exadel.training.service.TrainingEditService;
import org.exadel.training.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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
        if (te == null) {
            flagNew = true;
            te = new TrainingEdit();
        }
        if (requestMap.containsKey("title") && !requestMap.get("title").toString().equals(training.getTitle())) {
            flag = true;
            te.setTitle(requestMap.get("title").toString());
        }
        if (requestMap.containsKey("date") && new Timestamp(Long.parseLong(requestMap.get("date").toString())) != training.getDate()) {
            flag = true;
            te.setDate(new Timestamp(Long.parseLong(requestMap.get("date").toString())));
        }
        if (requestMap.containsKey("maxVisitorsCount") && Integer.parseInt(requestMap.get("maxVisitorsCount").toString()) != training.getMaxVisitorsCount()) {
            flag = true;
            te.setMaxVisitorsCount(Integer.parseInt(requestMap.get("maxVisitorsCount").toString()));
        }
        if (requestMap.containsKey("time") && requestMap.get("time").toString().equals(training.getTime())) {
            flag = true;
            te.setTime(requestMap.get("time").toString());
        }
        if (requestMap.containsKey("location") && Integer.parseInt(requestMap.get("location").toString()) != training.getLocation()) {
            flag = true;
            te.setLocation(Integer.parseInt(requestMap.get("location").toString()));
        }
        if (requestMap.containsKey("duration") && Integer.parseInt(requestMap.get("duration").toString()) != training.getDuration()) {
            flag = true;
            te.setDuration(Integer.parseInt(requestMap.get("duration").toString()));
        }
        if (requestMap.containsKey("description") && !requestMap.get("description").toString().equals(training.getDescription())) {
            flag = true;
            te.setDescription(requestMap.get("description").toString());
        }
        if (requestMap.containsKey("language") && !languageService.getLanguageByValue(requestMap.get("language").toString()).equals(training.getLanguage())) {
            flag = true;
            te.setLanguage(languageService.getLanguageByValue(requestMap.get("language").toString()));
        }
        te.setTraining(training);
        if (flag){
            if (flagNew) {
                trainingEditService.addEdit(te);
            } else {
                trainingEditService.updateEdit(te);
            }
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("Result", "OK!");
        return map;
    }

    @RequestMapping(value = "/rest/training/edit/{editId}/{approve}")
    public Map<String, Object> approveEdit(@PathVariable("approve") String approve, @PathVariable("editId") long id) {
        TrainingEdit te = trainingEditService.getEditByTrainingIfExist(id);
        Map<String, Object> map = new HashMap<>();
        if (te != null) {
            te.setIsApproved(approve.equals("approve"));
            trainingEditService.updateEdit(te);
            map.put("Result", "OK!");
        }
        return map;
    }
}

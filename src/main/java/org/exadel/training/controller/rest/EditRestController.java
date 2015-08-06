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

    @RequestMapping(value = "rest/edit/training", method = RequestMethod.POST)
    public Map<String, Object> editTraining(@RequestBody Map<String, Object> requestMap) {
        long id = Long.parseLong(requestMap.get("trainingId").toString());
        Training training = trainingService.getTrainingById(id);
        TrainingEdit te = trainingEditService.getEditByTrainingIfExist(id);
        boolean flagNew = false;
        if (te == null) {
            flagNew = true;
            te = new TrainingEdit();
        }
        if (requestMap.containsKey("title") && !requestMap.get("title").toString().equals(training.getTitle())) {
            te.setTitle(requestMap.get("title").toString());
        }
        if (requestMap.containsKey("date") && new Timestamp(Long.parseLong(requestMap.get("date").toString())) != training.getDate()) {
            te.setDate(new Timestamp(Long.parseLong(requestMap.get("date").toString())));
        }
        if (requestMap.containsKey("maxVisitorsCount") && Integer.parseInt(requestMap.get("maxVisitorsCount").toString()) != training.getMaxVisitorsCount()) {
            te.setMaxVisitorsCount(Integer.parseInt(requestMap.get("maxVisitorsCount").toString()));
        }
        if (requestMap.containsKey("time") && requestMap.get("time").toString().equals(training.getTime())) {
            te.setTime(requestMap.get("time").toString());
        }
        if (requestMap.containsKey("location") && Integer.parseInt(requestMap.get("location").toString()) != training.getLocation()) {
            te.setLocation(Integer.parseInt(requestMap.get("location").toString()));
        }
        if (requestMap.containsKey("duration") && Integer.parseInt(requestMap.get("duration").toString()) != training.getDuration()) {
            te.setDuration(Integer.parseInt(requestMap.get("duration").toString()));
        }
        if (requestMap.containsKey("description") && !requestMap.get("description").toString().equals(training.getDescription())){
            te.setDescription(requestMap.get("description").toString());
        }
        if (requestMap.containsKey("language") && !languageService.getLanguageByValue(requestMap.get("language").toString()).equals(training.getLanguage())){
            te.setLanguage(languageService.getLanguageByValue(requestMap.get("language").toString()));
        }
        te.setTraining(training);
        if(flagNew){
            trainingEditService.addEdit(te);
        } else {
            trainingEditService.updateEdit(te);
        }
        Map<String, Object> map =  new HashMap<>(1);
        map.put("Result", "OK!");
        return map;
    }
}

package org.exadel.training.controller.rest;

import org.exadel.training.model.Training;
import org.exadel.training.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class CalendarController {
    @Autowired
    TrainingService trainingService;

    @RequestMapping(value = "/q")
    public List<Training> getUsers(){
        return trainingService.getAllTraining();
    }
}

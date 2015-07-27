package org.exadel.training.controller.rest;

import org.exadel.training.model.Training;
import org.exadel.training.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CalendarController {
    @Autowired
    TrainingService trainingService;

    @RequestMapping(value = "rest/calendar")

    public List<Training> getUsers() {
        return trainingService.getAllTraining();
    }

    @RequestMapping(value = "rest/calendar/trainer", method = RequestMethod.GET)
    public List<Training> getByTrainer() {
        return trainingService.getTrainingsByTrainer(1);
    }

    @RequestMapping(value = "rest/calendar/visitor", method = RequestMethod.GET)
    public List<Training> getByVisitor() {
        return trainingService.getTrainingsByVisitor(1);
    }
}

package org.exadel.training.controller.rest;

import org.exadel.training.model.Training;
import org.exadel.training.model.User;
import org.exadel.training.service.TrainingService;
import org.exadel.training.service.TrainingServiceImpl;
import org.exadel.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TrainingRestController {
    @Autowired
    private TrainingService trainingService;

    @RequestMapping(value = "/rest/training", method = RequestMethod.POST)
    public String createTraining(@RequestBody Map<String, Object> map) {
        return "Hello";
    }
}
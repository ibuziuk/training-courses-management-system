package org.exadel.training.controller;

import org.exadel.training.model.Training;
import org.exadel.training.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/training")
public class AttendanceController {
    @Autowired
    TrainingService trainingService;

    @RequestMapping(value = "/attendance/{trainingId}", method = RequestMethod.GET)
    public String getAttendance(@PathVariable long trainingId) {
        Training training = trainingService.getTrainingById(trainingId);
        if (training != null) {
            return "attendance-book";
        }
        throw new ResourceNotFoundException();
    }
}

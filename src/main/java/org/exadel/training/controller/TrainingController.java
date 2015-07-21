package org.exadel.training.controller;

import org.exadel.training.model.Training;
import org.exadel.training.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
@RequestMapping("/training")
public class TrainingController {
    @Autowired
    private TrainingService trainingService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allTrainings(Map<String, Object> map) {
        map.put("training", new Training());
        map.put("futureList", trainingService.getFutureTrainings());
        map.put("pastList", trainingService.getPastTrainings());
        return "all-trainings";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newTraining() {
        return "new-training";
    }
}

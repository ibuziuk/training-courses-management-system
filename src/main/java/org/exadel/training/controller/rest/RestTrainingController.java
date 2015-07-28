package org.exadel.training.controller.rest;

import org.exadel.training.service.TrainingFeedbackService;
import org.exadel.training.service.TrainingRatingService;
import org.exadel.training.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RestTrainingController {
    @Autowired
    private TrainingService trainingService;

    @Autowired
    private TrainingFeedbackService trainingFeedbackService;

    @Autowired
    private TrainingRatingService trainingRatingService;

    @RequestMapping(value = "/d/{trainingId}", method = RequestMethod.GET)
    public Map<String, Object> getTraining(@PathVariable("trainingId") long trainingId){
        Map<String, Object> map = new HashMap<>();
        map.put("training", trainingService.getTrainingById(trainingId));
        map.put("raiting", trainingRatingService.getAverageRatingByTrainingID(trainingId));
        map.put("register", 0);
        map.put("vote", trainingRatingService.containsUserByTraining(trainingId, 1)
                || trainingFeedbackService.containsUserByTraining(trainingId, 1));
        return map;
    }
}

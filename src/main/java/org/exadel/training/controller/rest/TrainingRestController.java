package org.exadel.training.controller.rest;

import org.exadel.training.service.TrainingFeedbackService;
import org.exadel.training.service.TrainingRatingService;
import org.exadel.training.service.TrainingService;
import org.exadel.training.service.WaitingListService;
import org.exadel.training.utils.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/training")
public class TrainingRestController {
    @Autowired
    private TrainingService trainingService;

    @Autowired
    private TrainingFeedbackService trainingFeedbackService;

    @Autowired
    private TrainingRatingService trainingRatingService;

    @Autowired
    private WaitingListService waitingListService;

    @RequestMapping(value = "/{trainingId}", method = RequestMethod.GET)
    public Map<String, Object> getTraining(@PathVariable("trainingId") long trainingId){
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> map = new HashMap<>();
        map.put("training", trainingService.getTrainingById(trainingId));
        map.put("rating", trainingRatingService.getAverageRatingByTrainingID(trainingId));
        if (trainingService.containsVisitor(trainingId, userDetails.getId())){
            map.put("register", 0);
        } else if (waitingListService.checkingExist(trainingId, userDetails.getId())){
            map.put("register", 1);
        } else {
            map.put("register", 2);
        }
        map.put("vote", trainingRatingService.containsUserByTraining(trainingId, userDetails.getId())
                || trainingFeedbackService.containsUserByTraining(trainingId, userDetails.getId()));
        return map;
    }

    @RequestMapping(value = "/register/{trainingId}")
    public String registerForTraining(@PathVariable("trainingId") long trainingId){
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return trainingService.registerForTraining(trainingId, userDetails.getId());
    }

    @RequestMapping(value = "/remove/{trainingId}")
    public String removeFromTraining(@PathVariable("trainingId") long trainingId){
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return trainingService.removeVisitor(trainingId, userDetails.getId());
    }
}
package org.exadel.training.controller.rest;

import org.exadel.training.model.TrainingFeedback;
import org.exadel.training.service.TrainingFeedbackService;
import org.exadel.training.service.TrainingService;
import org.exadel.training.service.UserService;
import org.exadel.training.utils.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FeedbackController {
    @Autowired
    private TrainingFeedbackService trainingFeedbackService;

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/rest/feedback/training/{trainingId}", method = RequestMethod.POST)
    public Map<String, Object> addTrainingFeedback(@RequestBody Map<String, Object> map, @PathVariable("trainingId") long trainingId) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean flag = false;
        TrainingFeedback trainingFeedback = new TrainingFeedback();
        if (map.containsKey("impression")) {
            flag = true;
            trainingFeedback.setImpression(Integer.parseInt(map.get("impression").toString()));
        }
        if (map.containsKey("intelligibility")) {
            flag = true;
            trainingFeedback.setIntelligibility(Integer.parseInt(map.get("intelligibility").toString()));
        }
        if (map.containsKey("interest")) {
            flag = true;
            trainingFeedback.setInterest(Integer.parseInt(map.get("interest").toString()));
        }
        if (map.containsKey("update")) {
            flag = true;
            trainingFeedback.setUpdate(Integer.parseInt(map.get("update").toString()));
        }
        if (map.containsKey("effectiveness")) {
            flag = true;
            trainingFeedback.setEffectiveness(Integer.parseInt(map.get("effectiveness").toString()));
        }
        if (map.containsKey("recommending")) {
            flag = true;
            trainingFeedback.setRecommending(Boolean.parseBoolean(map.get("recommending").toString()));
        }
        if (map.containsKey("trainerRecommending")) {
            flag = true;
            trainingFeedback.setTrainerRecommending(Boolean.parseBoolean(map.get("trainerRecommending").toString()));
        }
        if (map.containsKey("text")) {
            flag = true;
            trainingFeedback.setText(map.get("text").toString());
        }
        if (map.containsKey("rate")) {
            flag = true;
            trainingFeedback.setStarCount(Integer.parseInt(map.get("rate").toString()));
        }
        if (flag) {
            trainingFeedback.setIsDeleted(false);
            trainingFeedback.setIsApproved(false);
            trainingFeedback.setDate(new Timestamp(new Date().getTime()));
            trainingFeedback.setUser(userService.getUserById(userDetails.getId()));
            trainingFeedback.setTraining(trainingService.getTrainingById(trainingId));
            trainingFeedbackService.addFeedback(trainingFeedback);
        }

        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("rating", trainingFeedbackService.getAverageRatingByTrainingID(trainingId));
        resultMap.put("feedbacks", trainingService.getTrainingById(trainingId).getTrainingFeedbacks());
        return resultMap;
    }
}

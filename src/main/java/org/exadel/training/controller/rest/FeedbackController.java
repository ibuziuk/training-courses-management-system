package org.exadel.training.controller.rest;

import org.exadel.training.model.TrainingFeedback;
import org.exadel.training.model.TrainingRating;
import org.exadel.training.service.TrainingFeedbackService;
import org.exadel.training.service.TrainingRatingService;
import org.exadel.training.service.TrainingService;
import org.exadel.training.service.UserService;
import org.exadel.training.utils.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

@RestController
public class FeedbackController {
    @Autowired
    private TrainingFeedbackService trainingFeedbackService;

    @Autowired
    private TrainingRatingService trainingRatingService;

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/rest/feedback/training/{trainingId}", method = RequestMethod.POST)
    public String addTrainingFeedback(@RequestBody Map<String, Object> map, @PathVariable("trainingId") long trainingId) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean flag = false;
        TrainingFeedback trainingFeedback = new TrainingFeedback();
        if (map.get("impression") != null) {
            flag = true;
            trainingFeedback.setImpression(Integer.parseInt((String) map.get("impression")));
        }
        if (map.get("intelligibility") != null) {
            flag = true;
            trainingFeedback.setIntelligibility(Integer.parseInt((String) map.get("intelligibility")));
        }
        if (map.get("interest") != null) {
            flag = true;
            trainingFeedback.setInterest(Integer.parseInt((String) map.get("interest")));
        }
        if (map.get("update") != null) {
            flag = true;
            trainingFeedback.setUpdate(Integer.parseInt((String) map.get("update")));
        }
        if (map.get("effectiveness") != null) {
            flag = true;
            trainingFeedback.setEffectiveness(Integer.parseInt((String) map.get("effectiveness")));
        }
        if (map.get("recommendation") != null) {
            flag = true;
            trainingFeedback.setRecommending(Boolean.parseBoolean((String) map.get("recommendation")));
        }
        if (map.get("trainer") != null) {
            flag = true;
            trainingFeedback.setTrainerRecommending(Boolean.parseBoolean((String) map.get("trainer")));
        }
        if (map.get("comment") != null) {
            flag = true;
            trainingFeedback.setText((String) map.get("comment"));
        }
        if (flag) {
            trainingFeedback.setIsDeleted(false);
            trainingFeedback.setIsApproved(false);
            trainingFeedback.setDate(new Timestamp(new Date().getTime()));
            trainingFeedback.setUser(userService.getUserById(userDetails.getId()));
            trainingFeedback.setTraining(trainingService.getTrainingById(trainingId));
            trainingFeedbackService.addFeedback(trainingFeedback);
        }

        if (map.get("rate") != null) {
            TrainingRating trainingRating = new TrainingRating();
            trainingRating.setStarCount(Integer.parseInt((String) map.get("rate")));
            trainingRating.setUser(userService.getUserById(userDetails.getId()));
            trainingRating.setTraining(trainingService.getTrainingById(trainingId));
            trainingRatingService.addTrainingRating(trainingRating);
        }
        return "OK!";
    }
}

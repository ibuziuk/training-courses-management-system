package org.exadel.training.controller.rest;

import org.exadel.training.model.TrainingFeedback;
import org.exadel.training.model.TrainingRating;
import org.exadel.training.service.TrainingFeedbackService;
import org.exadel.training.service.TrainingRatingService;
import org.exadel.training.service.TrainingService;
import org.exadel.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/q/{trainingId}", method = RequestMethod.POST)
    public String addTrainingFeedback(@RequestBody Map<String, Object> map, @PathVariable ("trainingId") long trainingId){
        TrainingFeedback trainingFeedback = new TrainingFeedback();
        if (map.get("impression") != null){
            trainingFeedback.setImpression(Integer.parseInt((String) map.get("impression")));
        }
        if (map.get("intelligibility") != null){
            trainingFeedback.setIntelligibility(Integer.parseInt((String) map.get("intelligibility")));
        }
        if(map.get("interest") != null){
            trainingFeedback.setInterest(Integer.parseInt((String) map.get("interest")));
        }
        if (map.get("update") != null){
            trainingFeedback.setUpdate(Integer.parseInt((String) map.get("update")));
        }
        if (map.get("effectiveness") != null){
            trainingFeedback.setEffectiveness(Integer.parseInt((String) map.get("effectiveness")));
        }
        if (map.get("recommendation") != null){
            trainingFeedback.setRecommending(Boolean.parseBoolean((String) map.get("recommendation")));
        }
        if (map.get("trainer") != null){
            trainingFeedback.setTrainerRecommending(Boolean.parseBoolean((String) map.get("trainer")));
        }
        if (map.get("comment") != null){
            trainingFeedback.setText((String) map.get("comment"));
        }
        trainingFeedback.setDate(new Timestamp(new Date().getTime()));
        trainingFeedback.setUser(userService.getUserById(1));
        trainingFeedback.setTraining(trainingService.getTrainingById(trainingId));
        trainingFeedbackService.addFeedback(trainingFeedback);

        if (map.get("rait") != null){
            TrainingRating trainingRating = new TrainingRating();
            trainingRating.setStarCount(Integer.parseInt((String) map.get("rait")));
            trainingRating.setUser(userService.getUserById(1));
            trainingRating.setTraining(trainingService.getTrainingById(trainingId));
            trainingRatingService.addTrainingRating(trainingRating);
        }
        return "OK!";
    }
}

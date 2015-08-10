package org.exadel.training.utils;

import org.exadel.training.model.EmployeeFeedback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsUtil {
    public static List<Object> customFeedbacjJson(List<EmployeeFeedback> employeeFeedbacks) {
        List<Object> feedbackJson = new ArrayList<>();
        for (EmployeeFeedback feedback : employeeFeedbacks) {
            Map<String, Object> json = new HashMap<>();
            json.put("trainingId", feedback.getTraining().getTrainingId());
            json.put("trainingTitle", feedback.getTraining().getTitle());
            json.put("date", feedback.getDate().getTime());
            json.put("trainerFirstName", feedback.getTraining().getTrainer().getFirstName());
            json.put("trainerLastName", feedback.getTraining().getTrainer().getLastName());
            json.put("text", feedback.getText());
            json.put("mark", feedback.getMark());
            feedbackJson.add(json);
        }
        return feedbackJson;
    }
}

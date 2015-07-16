package org.exadel.training.dao;

import org.exadel.training.model.TrainingFeedback;

import java.util.List;

public interface TrainingFeedbackDAO {
    void addFeedback(TrainingFeedback feedBack);

    List<TrainingFeedback> getAllFeedbacks();
}

package org.exadel.training.dao;

import org.exadel.training.model.TrainingFeedback;

import java.util.List;

public interface TrainingFeedbackDAO {
    void addFeedback(TrainingFeedback feedback);

    List<TrainingFeedback> getAllFeedbacks();

    boolean containsUserByTraining(long trainingId, long userId);
}

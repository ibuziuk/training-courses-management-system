package org.exadel.training.dao;

import org.exadel.training.model.TrainingFeedback;

import java.util.List;

public interface TrainingFeedbackDAO {
    void addFeedback(TrainingFeedback feedback);

    boolean containsUserByTraining(long trainingId, long userId);

    double getAverageRatingByTrainingID(long trainingId);

    TrainingFeedback getFeedbackById(long id);

    void updateFeedback (TrainingFeedback trainingFeedback);
}

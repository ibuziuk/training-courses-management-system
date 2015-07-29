package org.exadel.training.service;


import org.exadel.training.model.TrainingFeedback;

import java.util.List;

public interface TrainingFeedbackService {
    void addFeedback(TrainingFeedback feedback);

    List<TrainingFeedback> getAllFeedbacks();

    boolean containsUserByTraining(long trainingId, long userId);
}
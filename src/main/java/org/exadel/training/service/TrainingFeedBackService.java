package org.exadel.training.service;


import org.exadel.training.model.TrainingFeedback;

import java.util.List;

public interface TrainingFeedbackService {
    void addFeedback(TrainingFeedback feedBack);

    List<TrainingFeedback> getAllFeedbacks();
}
package org.exadel.training.service;


import org.exadel.training.model.TrainingFeed_back;

import java.util.List;

public interface TrainingFeed_backService {
    void addFeedback(TrainingFeed_back feedback);

    List<TrainingFeed_back> getAllFeedbacks();
}
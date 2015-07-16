package org.exadel.training.dao;

import org.exadel.training.model.TrainingFeed_back;

import java.util.List;

public interface TrainingFeed_backDAO {
    void addFeedback(TrainingFeed_back feedback);

    List<TrainingFeed_back> getAllFeedbacks();
}

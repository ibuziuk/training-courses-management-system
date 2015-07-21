package org.exadel.training.dao;

import org.exadel.training.model.TrainingAudience;

import java.util.List;

public interface TrainingAudienceDAO {
    void addTrainingAudience(TrainingAudience trainingAudience);

    List<TrainingAudience> getAllTrainingAudience();
}

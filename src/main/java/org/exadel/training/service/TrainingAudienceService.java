package org.exadel.training.service;

import org.exadel.training.model.TrainingAudience;

import java.util.List;

public interface TrainingAudienceService {
    void addTrainingAudience(TrainingAudience trainingAudience);

    List<TrainingAudience> getAllTrainingAudience();
}
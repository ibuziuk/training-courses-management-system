package org.exadel.training.service;

import org.exadel.training.model.TrainingEdit;

import java.util.List;

public interface TrainingEditService {
    void addEdit(TrainingEdit trainingEdit);

    void updateEdit(TrainingEdit trainingEdit);

    TrainingEdit getEditByTrainingIfExist(long trainingId);

    List<TrainingEdit> getAllEditsByTRaining(long trainingId);
}

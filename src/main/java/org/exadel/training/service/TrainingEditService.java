package org.exadel.training.service;

import org.exadel.training.model.TrainingEdit;

public interface TrainingEditService {
    void addEdit(TrainingEdit trainingEdit);

    void updateEdit(TrainingEdit trainingEdit);

    TrainingEdit getEditByTrainingIfExist(long trainingId);
}

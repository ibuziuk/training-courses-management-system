package org.exadel.training.dao;

import org.exadel.training.model.TrainingEdit;

public interface TrainingEditDAO {
    void addEdit(TrainingEdit trainingEdit);

    void updateEdit(TrainingEdit trainingEdit);

    TrainingEdit getEditByTrainingIfExist(long trainingId);
}

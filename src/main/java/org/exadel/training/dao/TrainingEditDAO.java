package org.exadel.training.dao;

import org.exadel.training.model.TrainingEdit;

import java.util.List;

public interface TrainingEditDAO {
    void addEdit(TrainingEdit trainingEdit);

    void updateEdit(TrainingEdit trainingEdit);

    TrainingEdit getEditByTrainingIfExist(long trainingId);

    List<TrainingEdit> getAllEditsByTRaining(long trainingId);
}

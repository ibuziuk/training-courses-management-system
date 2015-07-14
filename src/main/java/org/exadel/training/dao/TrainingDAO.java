package org.exadel.training.dao;

import org.exadel.training.model.Training;

import java.util.List;

public interface TrainingDAO {
    void addTraining(Training training);
    List getAllTraining();
    void updateTraining(Training training);
    void removeTraining(Training training);
}

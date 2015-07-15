package org.exadel.training.service;


import org.exadel.training.model.Training;

import java.util.List;

public interface TrainingService {
    void addTraining(Training training);
    List getAllTraining();
    void updateTraining(Training training);
    void removeTraining(Training training);
}

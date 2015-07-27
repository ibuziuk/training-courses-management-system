package org.exadel.training.service;


import org.exadel.training.model.Training;

import java.util.List;

public interface TrainingService {
    void addTraining(Training training);

    List<Training> getAllTraining();

    List<Training> getFutureTrainings();

    List<Training> getPastTrainings();

    List<Training> getTrainingsByName(String name);

    void updateTraining(Training training);

    void removeTrainingById(long id);

    Training getTrainingById(long id);

    List<Training> getTrainingsByTrainer(long id);

    List<Training> getTrainingsByVisitor(long id);

    boolean registerForTraining(long trainingId, long userId);
}

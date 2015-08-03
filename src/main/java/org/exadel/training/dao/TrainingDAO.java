package org.exadel.training.dao;

import org.exadel.training.model.Training;

import java.util.List;

public interface TrainingDAO {
    void addTraining(Training training);

    List<Training> getAllTrainings();

    List<Training> getFutureTrainings();

    List<Training> getPastTrainings();

    List<Training> getTrainingsByName(String name);

    void updateTraining(Training training);

    void removeTrainingById(long id);

    Training getTrainingById(long id);

    List<Training> getTrainingsByTrainer(long id);

    List<Training> getTrainingsByVisitor(long id);

    List<Training> getContinuousTrainings(long id);
}

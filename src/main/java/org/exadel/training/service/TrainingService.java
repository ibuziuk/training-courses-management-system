package org.exadel.training.service;


import org.exadel.training.model.Training;

import java.util.Comparator;
import java.util.List;

public interface TrainingService {
    void addTraining(Training training);

    List<Training> getAllTraining();

    List<Training> getFutureTrainings();

    List<Training> getPastTrainings();

    List<Training> getTrainingsByName(String name);

    void updateTraining(Training training);

    void removeTrainingById(long id);

    Comparator<Training> getComparatorByData();

    Training getTrainingById(long id);

    List<Training> getTrainingsByTrainer(long id);

    List<Training> getTrainingsByVisitor(long id);

    String registerForTraining(long trainingId, long userId);

    String removeVisitor(long trainingId, long userId);

    boolean containsVisitor(long trainingId, long userId);
}

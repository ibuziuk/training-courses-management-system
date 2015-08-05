package org.exadel.training.service;


import org.exadel.training.model.Training;

import java.util.List;
import java.util.Map;

public interface TrainingService {
    void addTraining(Training training);

    List<Training> getAllTraining();

    List<Training> getComeTrainings(String come, boolean admin);

    List<Training> getFutureTrainingsForScheduling();

    List<Training> getTrainingsByName(String name);

    void updateTraining(Training training);

    void removeTrainingById(long id);

    Training getTrainingById(long id);

    List<Training> getTrainingsByTrainer(long id);

    List<Training> getTrainingsByVisitor(long id);

    List<Training> getSomeTrainingOrderBy(String come, int pageNum, int pageSize, String sorting, String order, boolean admin);

    String registerForTraining(long trainingId, long userId);

    String removeVisitor(long trainingId, long userId);

    boolean containsVisitor(long trainingId, long userId);

    Map<String, Object> searchTrainings(int pageNumber, int pageSize, String searchType, String value);

    List<Training> getContinuousTrainings(long id);
}

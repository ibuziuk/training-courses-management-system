package org.exadel.training.service;


import org.exadel.training.model.Tag;
import org.exadel.training.model.Training;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TrainingService {
    void addTraining(Training training);

    List<Training> getAllTraining();

    List<Training> getFutureTrainingsForScheduling();

    List<Training> getTrainingsByName(String name);

    void updateTraining(Training training);

    void removeTrainingById(long id);

    Training getTrainingById(long id);

    List<Training> getTrainingsByTrainer(long id);

    List<Training> getTrainingsByVisitor(long id);

    Map<String, Object> getSomeTrainingOrderBy(String person, String come, int pageNum, int pageSize, String sorting, String order, boolean admin);

    String registerForTraining(long trainingId, long userId);

    String removeVisitor(long trainingId, long userId);

    boolean containsVisitor(long trainingId, long userId);

    Map<String, Object> searchTrainings(String person, String come, boolean isAdmin, int pageNumber, int pageSize, String searchType, String value);

    List<Training> getContinuousTrainings(long id);

    List<Training> getRecommendationsByUser(long userId);

    HashMap<Tag, Integer> getUserTags(long userId);
}

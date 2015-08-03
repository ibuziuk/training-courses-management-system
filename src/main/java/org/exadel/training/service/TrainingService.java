package org.exadel.training.service;


import org.exadel.training.model.Training;

import java.sql.Timestamp;
import java.util.List;

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

    List<Training> searchTrainingByTitle(String value);

    List<Training> searchTrainingsByDate(Timestamp date);

    List<Training> searchTrainingsByTime(String time);

    List<Training> searchTrainingsByLocation(int location);

    List<Training> searchTrainingsByTrainerName(String firstName, String lastName);
}

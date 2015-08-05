package org.exadel.training.dao;

import org.exadel.training.model.Training;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface TrainingDAO {
    void addTraining(Training training);

    List<Training> getAllTrainings();

    List<Training> getComeTrainings(String come, boolean admin);

    List<Training> getFutureTrainingsForScheduling();

    List<Training> getTrainingsByName(String name);

    void updateTraining(Training training);

    void removeTrainingById(long id);

    Training getTrainingById(long id);

    List<Training> getTrainingsByTrainer(long id);

    List<Training> getTrainingsByVisitor(long id);

    List<Training> getSomeTrainingOrderBy(String come, int pageNum, int pageSize, String sorting, String order, boolean admin);

    Map<String, Object> searchTrainingsByTitle(int pageNumber, int pageSize, String value);

    Map<String, Object> searchTrainingsByDate(int pageNumber, int pageSize, Timestamp date);

    Map<String, Object> searchTrainingsByTime(int pageNumber, int pageSize, String time);

    Map<String, Object> searchTrainingsByLocation(int pageNumber, int pageSize, int location);

    Map<String, Object> searchTrainingsByTrainerName(int pageNumber, int pageSize, String firstName, String lastName);

    List<Training> getContinuousTrainings(long id);
}

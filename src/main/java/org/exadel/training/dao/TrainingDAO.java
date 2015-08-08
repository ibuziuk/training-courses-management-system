package org.exadel.training.dao;

import org.exadel.training.model.Training;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface TrainingDAO {
    void addTraining(Training training);

    List<Training> getAllTrainings();

    List<Training> getFutureTrainingsForScheduling();

    List<Training> getTrainingsByName(String name);

    void updateTraining(Training training);

    void removeTrainingById(long id);

    Training getTrainingById(long id);

    List<Training> getTrainingsByTrainer(long id);

    List<Training> getTrainingsByVisitor(long id);

    Map<String, Object> getSomeTrainingOrderBy(String person, String come, int pageNum, int pageSize, String sorting, String order, boolean admin);

    Map<String, Object> searchTrainingsByTitle(String person, String come, boolean isAdmin, int pageNumber, int pageSize, String value);

    Map<String, Object> searchTrainingsByDate(String person, String come, boolean isAdmin, int pageNumber, int pageSize, Timestamp date);

    Map<String, Object> searchTrainingsByTime(String person, String come, boolean isAdmin, int pageNumber, int pageSize, String time);

    Map<String, Object> searchTrainingsByLocation(String person, String come, boolean isAdmin, int pageNumber, int pageSize, String location);

    Map<String, Object> searchTrainingsByTrainerName(String person, String come, boolean isAdmin, int pageNumber, int pageSize, String firstName, String lastName);

    List<Training> getContinuousTrainings(long id);
}

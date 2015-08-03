package org.exadel.training.dao;

import org.exadel.training.model.Training;

import java.sql.Timestamp;
import java.util.List;

public interface TrainingDAO {
    void addTraining(Training training);

    List<Training> getAllTrainings();

    List<Training> getComeTrainings(String come, boolean admin);

    List<Training> getTrainingsByName(String name);

    void updateTraining(Training training);

    void removeTrainingById(long id);

    Training getTrainingById(long id);

    List<Training> getTrainingsByTrainer(long id);

    List<Training> getTrainingsByVisitor(long id);

    List<Training> getSomeTrainingOrderBy(String come, int pageNum, int pageSize, String sorting, String order, boolean admin);

    List<Training> searchTrainingsByTitle(String value);

    List<Training> searchTrainingsByDate(Timestamp date);

    List<Training> searchTrainingsByTime(String time);

    List<Training> searchTrainingsByLocation(int location);

    List<Training> searchTrainingsByTrainerName(String firstName, String lastName);
}

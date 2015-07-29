package org.exadel.training.dao;

import org.exadel.training.model.TrainingRating;

public interface TrainingRatingDAO {
    void addTrainingRating(TrainingRating trainingRating);

    double getAverageRatingByTrainingID(long trainingID);

    boolean containsUserByTraining(long trainingId, long userId);
}

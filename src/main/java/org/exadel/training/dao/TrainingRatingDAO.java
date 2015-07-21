package org.exadel.training.dao;

import org.exadel.training.model.TrainingRating;

public interface TrainingRatingDAO {
    void addTrainingRating(TrainingRating trainingRating);

    TrainingRating getAverageRatingByTrainingID(long trainingID);
}

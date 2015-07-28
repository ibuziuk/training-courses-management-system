package org.exadel.training.service;


import org.exadel.training.model.TrainingRating;

public interface TrainingRatingService {
    void addTrainingRating(TrainingRating trainingRating);

    double getAverageRatingByTrainingID(long trainingID);

    boolean containsUserByTraining(long trainingId, long userId);
}
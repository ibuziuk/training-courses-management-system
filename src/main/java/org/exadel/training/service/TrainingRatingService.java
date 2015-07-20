package org.exadel.training.service;


import org.exadel.training.model.TrainingRating;

public interface TrainingRatingService {
    void addTrainingRating(TrainingRating trainingRating);

    TrainingRating getAverageRatingByTrainingID(long trainingID);
}
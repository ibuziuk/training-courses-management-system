package org.exadel.training.service;


import org.exadel.training.model.TrainingRaiting;

import java.util.List;

public interface TrainingRaitingService {
    void addTrainingRaiting(TrainingRaiting trainingRaiting);

    TrainingRaiting getAverageRaitingByTrainingID(long trainingID);
}
package org.exadel.training.dao;

import org.exadel.training.model.TrainingRaiting;

import java.util.List;

public interface TrainingRaitingDAO {
    void addTrainingRaiting(TrainingRaiting trainingRaiting);

    TrainingRaiting getAverageRaitingByTrainingID(long trainingID);
}

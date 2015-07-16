package org.exadel.training.dao;

import org.exadel.training.model.TrainingFeedBack;

import java.util.List;

public interface TrainingFeedBackDAO {
    void addFeedBack(TrainingFeedBack feedBack);

    List<TrainingFeedBack> getAllFeedBacks();
}

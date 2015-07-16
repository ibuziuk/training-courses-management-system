package org.exadel.training.service;


import org.exadel.training.model.TrainingFeedBack;

import java.util.List;

public interface TrainingFeedBackService {
    void addFeedBack(TrainingFeedBack feedBack);

    List<TrainingFeedBack> getAllFeedBacks();
}
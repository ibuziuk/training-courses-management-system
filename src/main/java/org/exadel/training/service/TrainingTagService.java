package org.exadel.training.service;

import org.exadel.training.model.TrainingTag;

import java.util.List;

public interface TrainingTagService {
    void addTrainingTag(TrainingTag trainingTag);

    List<TrainingTag> getAllTrainingTags();
}

package org.exadel.training.service;

import org.exadel.training.model.TrainingTag;

import java.util.List;

public interface CurrentTagService {
    void addCurrentTag(TrainingTag trainingTag);

    List<TrainingTag> getAllCurrentTags();
}

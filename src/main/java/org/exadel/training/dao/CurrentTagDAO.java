package org.exadel.training.dao;

import org.exadel.training.model.TrainingTag;

import java.util.List;

public interface CurrentTagDAO {
    void addCurrentTag(TrainingTag trainingTag);

    List<TrainingTag> getAllCurrentTags();
}

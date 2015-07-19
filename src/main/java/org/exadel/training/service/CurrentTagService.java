package org.exadel.training.service;

import org.exadel.training.model.CurrentTag;

import java.util.List;

public interface CurrentTagService {
    void addCurrentTag(CurrentTag currentTag);

    List<CurrentTag> getAllCurrentTags();
}

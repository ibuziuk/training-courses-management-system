package org.exadel.training.dao;

import org.exadel.training.model.CurrentTag;

import java.util.List;

public interface CurrentTagDAO {
    void addCurrentTag(CurrentTag currentTag);

    List<CurrentTag> getAllCurrentTags();
}

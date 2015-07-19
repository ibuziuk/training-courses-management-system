package org.exadel.training.dao;

import org.exadel.training.model.Tag;

public interface TagDAO {
    void addTag(Tag tag);

    Tag getTagById(int id);
}

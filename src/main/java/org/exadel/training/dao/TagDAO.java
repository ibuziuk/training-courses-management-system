package org.exadel.training.dao;

import org.exadel.training.model.Tag;

import java.util.List;

public interface TagDAO {
    void addTag(Tag tag);

    Tag getTagById(int id);

    List<Tag> getAllTags();
}

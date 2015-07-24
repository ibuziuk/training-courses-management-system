package org.exadel.training.dao;

import org.exadel.training.model.Tag;

import java.util.List;

public interface TagDAO {
    void addTag(Tag tag);
    List<Tag> getAllTags();
    Tag getTagByName(String name);
    Tag getTagById(int id);
}

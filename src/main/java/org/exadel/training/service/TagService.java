package org.exadel.training.service;

import org.exadel.training.model.Tag;

import java.util.List;

public interface TagService {
    void addTag(Tag tag);
    List<Tag> getAllTags();
    Tag getTagByName(String name);
    Tag getTagById(int id);
}

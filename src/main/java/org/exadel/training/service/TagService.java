package org.exadel.training.service;

import org.exadel.training.model.Tag;

import java.util.List;

public interface TagService {
    void addTag(Tag tag);

    Tag getTagById(int id);

    List<Tag> getAllTags();
}

package org.exadel.training.service;

import org.exadel.training.model.Tag;

public interface TagService {
    void addTag(Tag tag);

    Tag getTagById(int id);
}

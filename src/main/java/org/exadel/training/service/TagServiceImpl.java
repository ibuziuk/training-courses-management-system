package org.exadel.training.service;

import org.exadel.training.dao.TagDAO;
import org.exadel.training.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagDAO tagDAO;

    @Override
    @Transactional
    public void addTag(Tag tag) {
        tagDAO.addTag(tag);
    }

    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagDAO.getTagByName(name);
    }

    @Override
    @Transactional
    public Tag getTagById(int id) {
        return tagDAO.getTagById(id);
    }

    @Override
    @Transactional
    public List<Tag> getAllTags() {
        return tagDAO.getAllTags();
    }
}

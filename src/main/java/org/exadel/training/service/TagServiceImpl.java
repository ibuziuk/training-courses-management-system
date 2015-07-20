package org.exadel.training.service;

import org.exadel.training.dao.TagDAO;
import org.exadel.training.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagDAO tagDAO;

    @Override
    @Transactional
    public void addTag(Tag tag) {
        tagDAO.addTag(tag);
    }

    @Override
    @Transactional
    public Tag getTagById(int id) {
        return tagDAO.getTagById(id);
    }
}

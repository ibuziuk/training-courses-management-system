package org.exadel.training.service;

import org.exadel.training.dao.CurrentTagDAO;
import org.exadel.training.model.CurrentTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CurrentTagServiceImpl implements CurrentTagService{
    @Autowired
    private CurrentTagDAO currentTagDAO;

    @Override
    @Transactional
    public void addCurrentTag(CurrentTag currentTag){
        currentTagDAO.addCurrentTag(currentTag);
    }

    @Override
    @Transactional
    public List<CurrentTag> getAllCurrentTags(){
        return currentTagDAO.getAllCurrentTags();
    }
}

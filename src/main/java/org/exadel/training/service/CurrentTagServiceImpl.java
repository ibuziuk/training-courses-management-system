package org.exadel.training.service;

import org.exadel.training.dao.CurrentTagDAO;
import org.exadel.training.model.TrainingTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CurrentTagServiceImpl implements CurrentTagService {
    @Autowired
    private CurrentTagDAO currentTagDAO;

    @Override
    @Transactional
    public void addCurrentTag(TrainingTag trainingTag) {
        currentTagDAO.addCurrentTag(trainingTag);
    }

    @Override
    @Transactional
    public List<TrainingTag> getAllCurrentTags() {
        return currentTagDAO.getAllCurrentTags();
    }
}

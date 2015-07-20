package org.exadel.training.service;

import org.exadel.training.dao.TrainingTagDAO;
import org.exadel.training.model.TrainingTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TrainingTagServiceImpl implements TrainingTagService {
    @Autowired
    private TrainingTagDAO trainingTagDAO;

    @Override
    @Transactional
    public void addTrainingTag(TrainingTag trainingTag) {
        trainingTagDAO.addTrainingTag(trainingTag);
    }

    @Override
    @Transactional
    public List<TrainingTag> getAllTrainingTags() {
        return trainingTagDAO.getAllTrainingTags();
    }
}

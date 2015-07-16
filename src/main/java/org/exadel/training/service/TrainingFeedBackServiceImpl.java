package org.exadel.training.service;

import org.exadel.training.dao.TrainingFeedBackDAO;
import org.exadel.training.model.TrainingFeedBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TrainingFeedBackServiceImpl implements TrainingFeedBackService {
    @Autowired
    private TrainingFeedBackDAO trainingFeedBackDAO;

    @Override
    @Transactional
    public void addFeedBack(TrainingFeedBack feedBack) {
        trainingFeedBackDAO.addFeedBack(feedBack);
    }

    @Override
    @Transactional
    public List<TrainingFeedBack> getAllFeedBacks() {
        return trainingFeedBackDAO.getAllFeedBacks();
    }
}

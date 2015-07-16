package org.exadel.training.service;

import org.exadel.training.dao.TrainingFeed_backDAO;
import org.exadel.training.model.TrainingFeed_back;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TrainingFeed_backServiceImpl implements TrainingFeed_backService {
    @Autowired
    private TrainingFeed_backDAO trainingFeedbackDAO;

    @Override
    @Transactional
    public void addFeedback(TrainingFeed_back feedback) {
        trainingFeedbackDAO.addFeedback(feedback);
    }

    @Override
    @Transactional
    public List<TrainingFeed_back> getAllFeedbacks() {
        return trainingFeedbackDAO.getAllFeedbacks();
    }
}

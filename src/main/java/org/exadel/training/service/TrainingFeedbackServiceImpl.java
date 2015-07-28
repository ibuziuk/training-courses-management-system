package org.exadel.training.service;

import org.exadel.training.dao.TrainingFeedbackDAO;
import org.exadel.training.model.TrainingFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TrainingFeedbackServiceImpl implements TrainingFeedbackService {
    @Autowired
    private TrainingFeedbackDAO trainingFeedbackDAO;

    @Override
    @Transactional
    public void addFeedback(TrainingFeedback feedback) {

        trainingFeedbackDAO.addFeedback(feedback);
    }

    @Override
    @Transactional
    public List<TrainingFeedback> getAllFeedbacks() {
        return trainingFeedbackDAO.getAllFeedbacks();
    }
}

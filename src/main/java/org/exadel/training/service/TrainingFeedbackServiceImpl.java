package org.exadel.training.service;

import org.exadel.training.dao.TrainingFeedbackDAO;
import org.exadel.training.model.TrainingFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
    public boolean containsUserByTraining(long trainingId, long userId) {
        return trainingFeedbackDAO.containsUserByTraining(trainingId, userId);
    }

    @Override
    @Transactional
    public double getAverageRatingByTrainingID(long trainingId) {
        return trainingFeedbackDAO.getAverageRatingByTrainingID(trainingId);
    }

    @Override
    @Transactional
    public TrainingFeedback getFeedbackById(long id) {
        return trainingFeedbackDAO.getFeedbackById(id);
    }

    @Override
    @Transactional
    public void updateFeedback(TrainingFeedback trainingFeedback) {
        trainingFeedbackDAO.updateFeedback(trainingFeedback);
    }
}

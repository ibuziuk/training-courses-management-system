package org.exadel.training.service;

import org.exadel.training.dao.TrainerFeedbackDAO;
import org.exadel.training.model.TrainerFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerFeedbackServiceImpl implements TrainerFeedbackService {
    @Autowired
    TrainerFeedbackDAO trainerFeedbackDAO;

    @Override
    public void addTrainerFeedback(TrainerFeedback trainerFeedback) {
        trainerFeedbackDAO.addTrainerFeedback(trainerFeedback);
    }
}

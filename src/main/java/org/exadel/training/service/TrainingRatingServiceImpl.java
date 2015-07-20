package org.exadel.training.service;

import org.exadel.training.dao.TrainingRatingDAO;
import org.exadel.training.model.TrainingRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TrainingRatingServiceImpl implements TrainingRatingService {
    @Autowired
    private TrainingRatingDAO trainingRatingDAO;

    @Override
    @Transactional
    public void addTrainingRating(TrainingRating trainingRating) {
        trainingRatingDAO.addTrainingRating(trainingRating);
    }

    @Override
    @Transactional
    public TrainingRating getAverageRatingByTrainingID(long trainingID) {
        return trainingRatingDAO.getAverageRatingByTrainingID(trainingID);
    }
}

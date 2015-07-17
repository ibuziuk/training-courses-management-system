package org.exadel.training.service;

import org.exadel.training.dao.TrainingRaitingDAO;
import org.exadel.training.model.TrainingRaiting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TrainingRaitingServiceImpl implements TrainingRaitingService {
    @Autowired
    private TrainingRaitingDAO trainingRaitingDAO;

    @Override
    @Transactional
    public   void addTrainingRaiting(TrainingRaiting trainingRaiting) {
        trainingRaitingDAO.addTrainingRaiting(trainingRaiting);
    }

    @Override
    @Transactional
    public TrainingRaiting getAverageRaitingByTrainingID(long trainingID) {
        return trainingRaitingDAO.getAverageRaitingByTrainingID(trainingID);
    }
}

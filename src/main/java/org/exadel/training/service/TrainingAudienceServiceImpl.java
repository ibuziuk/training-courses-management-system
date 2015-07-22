package org.exadel.training.service;

import org.exadel.training.dao.TrainingAudienceDAO;
import org.exadel.training.model.TrainingAudience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TrainingAudienceServiceImpl implements TrainingAudienceService {
    @Autowired
    private TrainingAudienceDAO trainingAudienceDAO;

    @Override
    @Transactional
    public void addTrainingAudience(TrainingAudience trainingAudience){
        trainingAudienceDAO.addTrainingAudience(trainingAudience);
    }

    @Override
    @Transactional
    public List<TrainingAudience> getAllTrainingAudience(){
        return trainingAudienceDAO.getAllTrainingAudience();
    }
}

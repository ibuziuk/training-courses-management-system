package org.exadel.training.service;

import org.exadel.training.dao.TrainingEditDAO;
import org.exadel.training.model.TrainingEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TrainingEditServiceImpl implements TrainingEditService {
    @Autowired
    TrainingEditDAO trainingEditDAO;

    @Override
    @Transactional
    public void addEdit(TrainingEdit trainingEdit) {
        trainingEditDAO.addEdit(trainingEdit);
    }

    @Override
    @Transactional
    public void updateEdit(TrainingEdit trainingEdit) {
        trainingEditDAO.updateEdit(trainingEdit);
    }

    @Override
    @Transactional
    public TrainingEdit getEditByTrainingIfExist(long trainingId) {
        return trainingEditDAO.getEditByTrainingIfExist(trainingId);
    }

    @Override
    @Transactional
    public List<TrainingEdit> getAllEditsByTRaining(long trainingId) {
        return trainingEditDAO.getAllEditsByTRaining(trainingId);
    }
}

package org.exadel.training.service;

import org.exadel.training.dao.TrainingDAO;
import org.exadel.training.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {
    @Autowired
    private TrainingDAO trainingDAO;

    @Override
    @Transactional
    public void addTraining(Training training) {
        trainingDAO.addTraining(training);
    }

    @Override
    @Transactional
    public List<Training> getAllTraining() {
        return trainingDAO.getAllTrainings();
    }

    @Override
    @Transactional
    public List<Training> getFutureTrainings(){
        return trainingDAO.getFutureTrainings();
    }

    @Override
    @Transactional
    public List<Training> getPastTrainings(){
        return trainingDAO.getPastTrainings();
    }

    @Override
    @Transactional
    public List<Training> getTrainingsByName(String name) {
        return trainingDAO.getTrainingsByName(name);
    }

    @Override
    @Transactional
    public void updateTraining(Training training) {
        trainingDAO.updateTraining(training);
    }

    @Override
    @Transactional
    public void removeTraining(Training training) {
        trainingDAO.removeTraining(training);
    }

    @Override
    @Transactional
    public Training getTrainingById(long id) {
        return trainingDAO.getTrainingById(id);
    }
}

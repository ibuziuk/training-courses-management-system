package org.exadel.training.service;

import org.exadel.training.dao.TrainingDAO;
import org.exadel.training.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
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
    public List<Training> getFutureTrainings() {
        return trainingDAO.getFutureTrainings();
    }

    @Override
    @Transactional
    public List<Training> getPastTrainings() {
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
    public void removeTrainingById(long id) {
        trainingDAO.removeTrainingById(id);
    }

    @Override
    public Comparator<Training> getComparatorByData() {
        return new Comparator<Training>() {
            @Override
            public int compare(Training obj1, Training obj2) {
                return obj1.getDate().compareTo(obj2.getDate());
            }
        };
    }

    @Override
    @Transactional
    public Training getTrainingById(long id) {
        return trainingDAO.getTrainingById(id);
    }

    @Override
    @Transactional
    public List<Training> getTrainingsByTrainer(long id) {
        return trainingDAO.getTrainingsByTrainer(id);
    }

    @Override
    @Transactional
    public List<Training> getTrainingsByVisitor(long id) {
        return trainingDAO.getTrainingsByVisitor(id);
    }
}

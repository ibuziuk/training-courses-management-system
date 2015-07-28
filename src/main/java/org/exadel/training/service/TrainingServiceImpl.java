package org.exadel.training.service;

import org.exadel.training.dao.TrainingDAO;
import org.exadel.training.dao.UserDAO;
import org.exadel.training.dao.WaitingListDAO;
import org.exadel.training.model.Training;
import org.exadel.training.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {
    @Autowired
    private TrainingDAO trainingDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private WaitingListDAO waitingListDAO;

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

    @Override
    @Transactional
    public String registerForTraining(long trainingId, long userId){
        Training training = trainingDAO.getTrainingById(trainingId);
        User user = userDAO.getUserById(userId);
        if (training.getVisitors().contains(user)){
            return "Already exist.";
        }
        if (training.getVisitors().size() < training.getMaxVisitorsCount()){
            training.getVisitors().add(user);
            return "Success";
        }
        return waitingListDAO.addVisitor(trainingId, userId);
    }

    @Override
    @Transactional
    public String removeVisitor(long trainingId, long userId){
        Training training = trainingDAO.getTrainingById(trainingId);
        User user = userDAO.getUserById(userId);
        if (training.getVisitors().contains(user)){
            training.getVisitors().remove(user);
            return "Success.";
        }
        return waitingListDAO.removeVisitor(trainingId, userId);
    }

    @Override
    @Transactional
    public boolean containsVisitor(long trainingId, long userId){
        Training training = trainingDAO.getTrainingById(trainingId);
        User user = userDAO.getUserById(userId);
        return training.getVisitors().contains(user);
    }
}

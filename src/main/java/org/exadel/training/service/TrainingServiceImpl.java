package org.exadel.training.service;

import org.exadel.training.dao.TrainingDAO;
import org.exadel.training.dao.UserDAO;
import org.exadel.training.dao.WaitingListDAO;
import org.exadel.training.model.Training;
import org.exadel.training.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;

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
    public List<Training> getComeTrainings(String come, boolean admin) {
        return trainingDAO.getComeTrainings(come, admin);
    }

    @Override
    @Transactional
    public List<Training> getFutureTrainingsForScheduling() {
        return trainingDAO.getFutureTrainingsForScheduling();
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
    public List<Training> getSomeTrainingOrderBy(String come, int pageNum, int pageSize, String sorting, String order, boolean admin) {
        return trainingDAO.getSomeTrainingOrderBy(come, pageNum, pageSize, sorting, order, admin);
    }

    @Override
    @Transactional
    public String registerForTraining(long trainingId, long userId) {
        Training training = trainingDAO.getTrainingById(trainingId);
        User user = userDAO.getUserById(userId);
        if (training.getVisitors().contains(user)) {
            return "Already exist.";
        }
        List<Training> trainings = new ArrayList<>(1);
        trainings.add(training);
        if (training.getContinuous()) {
            trainings = getContinuousTrainings(trainingId);
        }
        if (training.getVisitors().size() < training.getMaxVisitorsCount()) {
            for (Training elem : trainings) {
                elem.getVisitors().add(user);
            }
            return "Success";
        }
        return waitingListDAO.addVisitor(trainingId, userId);
    }

    @Override
    @Transactional
    public String removeVisitor(long trainingId, long userId) {
        Training training = trainingDAO.getTrainingById(trainingId);
        User user = userDAO.getUserById(userId);
        if (training.getVisitors().contains(user)) {
            List<Training> trainings = new ArrayList<>(1);
            trainings.add(training);
            if (training.getContinuous()) {
                trainings = getContinuousTrainings(trainingId);
            }
            User next = waitingListDAO.getNext(trainingId);
            for (Training elem : trainings) {
                elem.getVisitors().remove(user);
                if (next != null) {
                    elem.getVisitors().add(next);
                }
                elem.getExVisitors().add(user);
            }
            return "Success.";
        }
        return waitingListDAO.removeVisitor(trainingId, userId);
    }

    @Override
    @Transactional
    public boolean containsVisitor(long trainingId, long userId) {
        Training training = trainingDAO.getTrainingById(trainingId);
        User user = userDAO.getUserById(userId);
        return training.getVisitors().contains(user);
    }

    @Override
    @Transactional
    public Map<String, Object> searchTrainings(int pageNumber, int pageSize, String searchType, String value) {
        switch (searchType) {
            case "title":
                return trainingDAO.searchTrainingsByTitle(pageNumber, pageSize, value);
            case "date":
                return trainingDAO.searchTrainingsByDate(pageNumber, pageSize, new Timestamp(Long.parseLong(value)));
            case "time":
                return trainingDAO.searchTrainingsByTime(pageNumber, pageSize, value);
            case "location":
                return trainingDAO.searchTrainingsByLocation(pageNumber, pageSize, Integer.parseInt(value));
            case "trainerName":
                List<String> str = Arrays.asList(value.split(" "));
                if (str.size() > 1) {
                    return trainingDAO.searchTrainingsByTrainerName(pageNumber, pageSize, str.get(0), str.get(1));
                }
                return trainingDAO.searchTrainingsByTrainerName(pageNumber, pageSize, str.get(0), "");
            default:
                Map<String, Object> map = new HashMap<>(1);
                map.put("size", 0);
                return map;
        }
    }

    @Override
    @Transactional
    public List<Training> getContinuousTrainings(long id) {
        return trainingDAO.getContinuousTrainings(id);
    }
}

package org.exadel.training.service;

import org.exadel.training.dao.RegularLessonDAO;
import org.exadel.training.dao.TrainingDAO;
import org.exadel.training.dao.UserDAO;
import org.exadel.training.dao.WaitingListDAO;
import org.exadel.training.model.Tag;
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

    @Autowired
    private RegularLessonDAO regularLessonDAO;

    @Autowired
    private NotificationService notificationService;

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
    public Map<String, Object> getSomeTrainingOrderBy(String person, String come, int pageNum, int pageSize, String sorting, String order, boolean admin) {
        return trainingDAO.getSomeTrainingOrderBy(person, come, pageNum, pageSize, sorting, order, admin);
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
            notificationService.addNotification(trainingId, next.getUserId(), 6);
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
    public Map<String, Object> searchTrainings(String person, String come, boolean isAdmin, int pageNumber, int pageSize, String searchType, String value) {
        switch (searchType) {
            case "title":
                return trainingDAO.searchTrainingsByTitle(person, come, isAdmin, pageNumber, pageSize, value);
            case "date":
                return trainingDAO.searchTrainingsByDate(person, come, isAdmin, pageNumber, pageSize, new Timestamp(Long.parseLong(value)));
            case "time":
                return trainingDAO.searchTrainingsByTime(person, come, isAdmin, pageNumber, pageSize, value);
            case "location":
                return trainingDAO.searchTrainingsByLocation(person, come, isAdmin, pageNumber, pageSize, value);
            case "coach":
                List<String> str = Arrays.asList(value.split(" "));
                if (str.size() > 1) {
                    return trainingDAO.searchTrainingsByTrainerName(person, come, isAdmin, pageNumber, pageSize, str.get(0), str.get(1));
                }
                return trainingDAO.searchTrainingsByTrainerName(person, come, isAdmin, pageNumber, pageSize, str.get(0), "");
            case "tags":
                return trainingDAO.searchTrainingsByTags(person, come, isAdmin, pageNumber, pageSize, value.split("[/s]+"));
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

    @Override
    @Transactional
    public List<Training> getRecommendationsByUser(long userId) {
        List<Training> trainings = trainingDAO.getTrainingsByVisitor(userId);
        HashMap<Tag, Integer> allTags = new HashMap<>();

        TreeMap<Integer, Training> allTrainings = new TreeMap<>((obj1, obj2) -> {
            if (obj1 > obj2)
                return 1;
            else return -1;
        });

        for (Training all : trainings) {
            Set<Tag> tag = all.getTags();
            for (Tag trainingTag : tag) {
                if (allTags.containsKey(trainingTag)) {
                    allTags.put(trainingTag, allTags.get(trainingTag) + 1);
                } else {
                    allTags.put(trainingTag, 1);
                }
            }
        }

        trainings = trainingDAO.getFutureTrainingsForScheduling();
        for (Training all : trainings) {
            Set<Tag> tag = all.getTags();
            int sum = 0;
            for (Tag trainingTag : tag) {
                if (allTags.containsKey(trainingTag)) {
                    sum += allTags.get(trainingTag);
                }
            }
            allTrainings.put(sum, all);
        }

        List<Training> result = new LinkedList<>();
        for (int i = 0; i < 10 && !allTrainings.isEmpty(); i++) {
            Training training = allTrainings.pollLastEntry().getValue();
            if (training.getTrainer().getUserId() != userId && training.getVisitors().size() < training.getMaxVisitorsCount())
                result.add(training);
        }
        return result;
    }

    @Override
    @Transactional
    public HashMap<Tag, Integer> getUserTags(long userId) {
        List<Training> trainings = trainingDAO.getTrainingsByVisitor(userId);
        HashMap<Tag, Integer> allTags = new HashMap<>();

        for (Training all : trainings) {
            Set<Tag> tag = all.getTags();
            for (Tag trainingTag : tag) {
                if (allTags.containsKey(trainingTag)) {
                    allTags.put(trainingTag, allTags.get(trainingTag) + 1);
                } else {
                    allTags.put(trainingTag, 1);
                }
            }
        }
        return allTags;
    }
}

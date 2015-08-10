package org.exadel.training.service;

import org.exadel.training.dao.EmployeeFeedbackDAO;
import org.exadel.training.model.EmployeeFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EmployeeFeedbackServiceImpl implements EmployeeFeedbackService {
    @Autowired
    private EmployeeFeedbackDAO employeeFeedbackDAO;

    @Override
    @Transactional
    public void addFeedback(EmployeeFeedback feedback) {
        employeeFeedbackDAO.addFeedback(feedback);
    }

    @Override
    @Transactional
    public List<EmployeeFeedback> getAllFeedbacks() {
        return employeeFeedbackDAO.getAllFeedbacks();
    }

    @Override
    @Transactional
    public List<EmployeeFeedback> getFeedbacksByUserAndTraining(long userId, long trainingId) {
        return employeeFeedbackDAO.getFeedbacksByUserAndTraining(userId, trainingId);
    }

    @Override
    @Transactional
    public List<EmployeeFeedback> getFeedbacksByUser(long userId) {
        return employeeFeedbackDAO.getFeedbacksByUser(userId);
    }
}

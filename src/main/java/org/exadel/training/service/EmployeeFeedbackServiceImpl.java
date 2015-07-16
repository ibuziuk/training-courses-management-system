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
    public void addFeedback(EmployeeFeedback feedBack) {
        employeeFeedbackDAO.addFeedback(feedBack);
    }

    @Override
    @Transactional
    public List<EmployeeFeedback> getAllFeedbacks() {
        return employeeFeedbackDAO.getAllFeedbacks();
    }
}

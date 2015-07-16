package org.exadel.training.dao;

import org.exadel.training.model.EmployeeFeedback;

import java.util.List;

public interface EmployeeFeedbackDAO {
    void addFeedback(EmployeeFeedback feedback);

    List<EmployeeFeedback> getAllFeedbacks();
}

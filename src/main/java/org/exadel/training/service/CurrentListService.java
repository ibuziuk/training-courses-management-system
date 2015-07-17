package org.exadel.training.service;


import org.exadel.training.model.CurrentList;

import java.util.List;

public interface CurrentListService {
    void addVisitor(long userID, long trainingID);

    List<Long> getAllVisitors();

    void removeVisitor(long userID, long trainingID);
}
package org.exadel.training.service;


import org.exadel.training.model.WaitingList;

import java.util.List;

public interface WaitingListService {
    void addVisitor(long userID, long trainingID);

    List<Long> getAllVisitors();

    void removeVisitor(long userID, long trainingID);
}
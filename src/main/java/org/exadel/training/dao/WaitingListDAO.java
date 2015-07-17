package org.exadel.training.dao;

import org.exadel.training.model.WaitingList;

import java.util.List;

public interface WaitingListDAO {
    void addVisitor(long userID, long trainingID);

    List<Long> getAllVisitors();

    void removeVisitor(long userID, long trainingID);
}

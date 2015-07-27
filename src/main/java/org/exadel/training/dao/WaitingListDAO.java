package org.exadel.training.dao;

import java.util.List;

public interface WaitingListDAO {
    void addVisitor(long trainingID, long userID);

    List<Long> getAllVisitors();

    void removeVisitor(long trainingID, long userID);
}

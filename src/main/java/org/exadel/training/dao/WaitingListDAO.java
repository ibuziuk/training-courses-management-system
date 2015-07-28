package org.exadel.training.dao;

import java.util.List;

public interface WaitingListDAO {
    String addVisitor(long trainingID, long userID);

    List<Long> getAllVisitors();

    String removeVisitor(long trainingID, long userID);
}

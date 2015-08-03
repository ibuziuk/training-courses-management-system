package org.exadel.training.dao;

import org.exadel.training.model.User;

import java.util.List;

public interface WaitingListDAO {
    String addVisitor(long trainingID, long userID);

    List<Long> getAllVisitors();

    String removeVisitor(long trainingID, long userID);

    boolean checkingExist(long trainingId, long userId);

    User getNext(long trainingId);
}

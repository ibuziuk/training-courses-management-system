package org.exadel.training.service;


import java.util.List;

public interface WaitingListService {
    String addVisitor(long userID, long trainingID);

    List<Long> getAllVisitors();

    String removeVisitor(long userID, long trainingID);

    boolean checkingExist(long trainingId, long userId);
}
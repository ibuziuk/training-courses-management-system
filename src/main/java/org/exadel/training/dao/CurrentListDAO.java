package org.exadel.training.dao;

import org.exadel.training.model.CurrentList;

import java.util.List;

public interface CurrentListDAO {
    void addVisitor(long userID, long trainingID);

    List<Long> getAllVisitors();

    void removeVisitor(long userID, long trainingID);
}

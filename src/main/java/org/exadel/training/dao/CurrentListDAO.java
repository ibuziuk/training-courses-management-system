package org.exadel.training.dao;

import org.exadel.training.model.CurrentList;

import java.util.List;

public interface CurrentListDAO {
    void addVisitor(long userId, long trainingId);

    List<Long> getAllVisitors();

    void removeVisitor(long userId, long trainingId);
}

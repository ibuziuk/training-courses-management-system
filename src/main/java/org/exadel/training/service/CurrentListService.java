package org.exadel.training.service;


import org.exadel.training.model.CurrentList;

import java.util.List;

public interface CurrentListService {
    void addVisitor(long userId, long trainingId);

    List<Long> getAllVisitors();

    void removeVisitor(long userId, long trainingId);
}
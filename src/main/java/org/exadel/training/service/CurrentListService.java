package org.exadel.training.service;


import org.exadel.training.model.Training;
import org.exadel.training.model.User;

import java.util.List;

public interface CurrentListService {
    void addVisitor(User visitor, Training training);

    List<Long> getAllVisitors();

    void removeVisitor(User visitor, Training training);
}
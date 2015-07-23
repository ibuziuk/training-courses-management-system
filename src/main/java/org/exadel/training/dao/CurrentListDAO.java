package org.exadel.training.dao;

import org.exadel.training.model.CurrentList;
import org.exadel.training.model.Training;
import org.exadel.training.model.User;

import java.util.List;

public interface CurrentListDAO {
    void addVisitor(User visitor, Training training);

    List<Long> getAllVisitors();

    void removeVisitor(User visitor, Training training);
}

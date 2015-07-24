package org.exadel.training.service;

import org.exadel.training.dao.CurrentListDAO;
import org.exadel.training.model.Training;
import org.exadel.training.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CurrentListServiceImpl implements CurrentListService {
    @Autowired
    private CurrentListDAO currentListDAO;

    @Override
    @Transactional
    public void addVisitor(User visitor, Training training) {
        currentListDAO.addVisitor(visitor, training);
    }

    @Override
    @Transactional
    public List<Long> getAllVisitors() {
        return currentListDAO.getAllVisitors();
    }

    @Override
    @Transactional
    public void removeVisitor(User visitor, Training training) {
        currentListDAO.removeVisitor(visitor, training);
    }
}

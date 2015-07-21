package org.exadel.training.service;

import org.exadel.training.dao.CurrentListDAO;
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
    public void addVisitor(long userId, long trainingId) {
        currentListDAO.addVisitor(userId, trainingId);
    }

    @Override
    @Transactional
    public List<Long> getAllVisitors() {
        return currentListDAO.getAllVisitors();
    }

    @Override
    @Transactional
    public void removeVisitor(long userId, long trainingId) {
        currentListDAO.removeVisitor(userId, trainingId);
    }
}

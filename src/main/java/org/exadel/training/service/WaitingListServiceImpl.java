package org.exadel.training.service;

import org.exadel.training.dao.WaitingListDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class WaitingListServiceImpl implements WaitingListService {
    @Autowired
    private WaitingListDAO waitingListDAO;

    @Override
    @Transactional
    public String addVisitor(long userID, long trainingID) {
        return waitingListDAO.addVisitor(userID, trainingID);
    }

    @Override
    @Transactional
    public List<Long> getAllVisitors() {
        return waitingListDAO.getAllVisitors();
    }

    @Override
    @Transactional
    public String removeVisitor(long userID, long trainingID) {
        return waitingListDAO.removeVisitor(userID, trainingID);
    }

    @Override
    @Transactional
    public boolean checkingExist(long trainingId, long userId) {
        return waitingListDAO.checkingExist(trainingId, userId);
    }
}

package org.exadel.training.service;

import org.exadel.training.dao.AbsenceLessonDAO;
import org.exadel.training.model.AbsenceLesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AbsenceLessonServiceImpl implements AbsenceLessonService {
    @Autowired
    AbsenceLessonDAO absenceLessonDAO;

    @Override
    @Transactional
    public void addAbsence(AbsenceLesson absence) {
        absenceLessonDAO.addAbsence(absence);
    }

    @Override
    @Transactional
    public void updateAbsence(AbsenceLesson absence) {
        absenceLessonDAO.updateAbsence(absence);
    }

    @Override
    @Transactional
    public List<AbsenceLesson> getAllAbsences() {
        return absenceLessonDAO.getAllAbsences();
    }

    @Override
    @Transactional
    public List<AbsenceLesson> getAbsencesByUser(long userId) {
        return absenceLessonDAO.getAbsencesByUser(userId);
    }

    @Override
    @Transactional
    public List<AbsenceLesson> getAbsencesByUserAndTraining(long userId, long trainingId) {
        return absenceLessonDAO.getAbsencesByUserAndTraining(userId, trainingId);
    }
}

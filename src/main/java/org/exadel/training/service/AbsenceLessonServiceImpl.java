package org.exadel.training.service;

import org.exadel.training.dao.AbsenceLessonDAO;
import org.exadel.training.model.Absence;
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
    public void addAbsence(Absence absence) {
        absenceLessonDAO.addAbsence(absence);
    }

    @Override
    @Transactional
    public void updateAbsence(Absence absence) {
        absenceLessonDAO.updateAbsence(absence);
    }

    @Override
    @Transactional
    public List<Absence> getAllAbsences() {
        return absenceLessonDAO.getAllAbsences();
    }
}

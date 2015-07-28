package org.exadel.training.service;

import org.exadel.training.dao.AbsenceDAO;
import org.exadel.training.model.Absence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AbsenceServiceImpl implements AbsenceService {
    @Autowired
    private AbsenceDAO absenceDAO;

    @Override
    @Transactional
    public void addAbsence(Absence absence) {
        absenceDAO.addAbsence(absence);
    }

    @Override
    @Transactional
    public void updateAbsence(Absence absence) {
        absenceDAO.updateAbsence(absence);
    }

    @Override
    @Transactional
    public List<Absence> getAllAbsences() {
        return absenceDAO.getAllAbsences();
    }
}

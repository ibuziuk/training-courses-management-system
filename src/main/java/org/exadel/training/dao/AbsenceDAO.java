package org.exadel.training.dao;

import org.exadel.training.model.Absence;

import java.util.List;

public interface AbsenceDAO {
    void addAbsence(Absence absence);

    List<Absence> getAllAbsences();
}

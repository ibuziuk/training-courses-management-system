package org.exadel.training.dao;

import org.exadel.training.model.Absence;

import java.util.List;

public interface AbsenceDAO {
    void addAbsence(Absence absence);

    void updateAbsence(Absence absence);

    List<Absence> getAllAbsences();

    List<Absence> getAbsencesByUser(long userId);

    Absence getAbsenceByUserAndTraining(long userId, long trainingId);
}

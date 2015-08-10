package org.exadel.training.service;


import org.exadel.training.model.Absence;

import java.util.List;

public interface AbsenceService {
    void addAbsence(Absence absence);

    void updateAbsence(Absence absence);

    List<Absence> getAllAbsences();

    List<Absence> getAbsencesByUser(long userId);

    Absence getAbsenceByUserAndTraining(long userId, long trainingId);
}
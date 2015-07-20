package org.exadel.training.service;


import org.exadel.training.model.Absence;

import java.util.List;

public interface AbsenceService {
    void addAbsence(Absence absence);

    List<Absence> getAllAbsences();
}
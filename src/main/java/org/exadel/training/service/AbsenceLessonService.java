package org.exadel.training.service;

import org.exadel.training.model.Absence;

import java.util.List;

public interface AbsenceLessonService {
    void addAbsence(Absence absence);

    void updateAbsence(Absence absence);

    List<Absence> getAllAbsences();
}

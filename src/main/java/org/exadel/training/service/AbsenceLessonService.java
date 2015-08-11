package org.exadel.training.service;

import org.exadel.training.model.AbsenceLesson;

import java.util.List;

public interface AbsenceLessonService {
    void addAbsence(AbsenceLesson absence);

    void updateAbsence(AbsenceLesson absence);

    List<AbsenceLesson> getAllAbsences();

    List<AbsenceLesson> getAbsencesByUser(long userId);

    List<AbsenceLesson> getAbsencesByUserAndTraining(long userId, long trainingId);

    List<AbsenceLesson> getAbsencesByTraining(long trainingId);
}

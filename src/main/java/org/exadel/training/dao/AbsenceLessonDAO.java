package org.exadel.training.dao;

import org.exadel.training.model.AbsenceLesson;

import java.util.List;

public interface AbsenceLessonDAO {
    void addAbsence(AbsenceLesson absence);

    void updateAbsence(AbsenceLesson absence);

    List<AbsenceLesson> getAllAbsences();

    List<AbsenceLesson> getAbsencesByUser(long userId);

    List<AbsenceLesson> getAbsencesByUserAndTraining(long userId, long trainingId);
}

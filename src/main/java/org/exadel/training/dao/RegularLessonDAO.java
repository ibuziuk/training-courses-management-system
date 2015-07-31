package org.exadel.training.dao;

import org.exadel.training.model.RegularLesson;

import java.util.List;

public interface RegularLessonDAO {
    void addRegularLesson(RegularLesson regularLesson);

    void updateRegularLesson(RegularLesson regularLesson);

    void removeRegularLesson(RegularLesson regularLesson);

    RegularLesson getRegularLessonById(long id);

    List<RegularLesson> getFutureRegularLessons();
}

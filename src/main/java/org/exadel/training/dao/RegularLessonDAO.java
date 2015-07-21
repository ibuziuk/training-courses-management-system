package org.exadel.training.dao;

import org.exadel.training.model.RegularLesson;

public interface RegularLessonDAO {
    void addRegularLesson(RegularLesson regularLesson);

    void updateRegularLesson(RegularLesson regularLesson);

    void removeRegularLesson(RegularLesson regularLesson);

    RegularLesson getRegularLessonById(long id);
}

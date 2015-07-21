package org.exadel.training.service;

import org.exadel.training.model.RegularLesson;

public interface RegularLessonService {
    void addRegularLesson(RegularLesson regularLesson);

    void updateRegularLesson(RegularLesson regularLesson);

    void removeRegularLesson(RegularLesson regularLesson);

    RegularLesson getRegularLessonById(long id);
}

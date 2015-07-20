package org.exadel.training.service;

import org.exadel.training.model.RegularLesson;

import java.util.List;

public interface RegularLessonService {
    void addRegularLesson(RegularLesson regularLesson);

    void updateRegularLesson(RegularLesson regularLesson);

    void removeRegularLesson(RegularLesson regularLesson);

    RegularLesson getRegularLessonById(long id);
}

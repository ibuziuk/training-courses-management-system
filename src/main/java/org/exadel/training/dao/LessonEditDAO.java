package org.exadel.training.dao;

import org.exadel.training.model.LessonEdit;

import java.util.List;

public interface LessonEditDAO {
    void addEdit(LessonEdit lessonEdit);

    void updateEdit(LessonEdit lessonEdit);

    LessonEdit getEditByLessonIfExist(long lessonId);

    List<LessonEdit> getAllEditsByLesson(long lessonId);
}

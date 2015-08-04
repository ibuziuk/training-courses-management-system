package org.exadel.training.service;

import org.exadel.training.dao.LessonEditDAO;
import org.exadel.training.model.LessonEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LessonEditServiceImpl implements LessonEditService {
    @Autowired
    LessonEditDAO lessonEditDAO;

    @Override
    @Transactional
    public void addEdit(LessonEdit lessonEdit) {
        lessonEditDAO.addEdit(lessonEdit);
    }

    @Override
    @Transactional
    public void updateEdit(LessonEdit lessonEdit) {
        lessonEditDAO.updateEdit(lessonEdit);
    }

    @Override
    @Transactional
    public LessonEdit getEditByLessonIfExist(long lessonId) {
        return lessonEditDAO.getEditByLessonIfExist(lessonId);
    }

    @Override
    @Transactional
    public List<LessonEdit> getAllEditsByLesson(long lessonId) {
        return lessonEditDAO.getAllEditsByLesson(lessonId);
    }
}

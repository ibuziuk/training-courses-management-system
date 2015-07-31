package org.exadel.training.service;

import org.exadel.training.dao.RegularLessonDAO;
import org.exadel.training.model.RegularLesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RegularLessonServiceImpl implements RegularLessonService {
    @Autowired
    private RegularLessonDAO regularLessonDAO;

    @Override
    @Transactional
    public void addRegularLesson(RegularLesson regularLesson) {
        regularLessonDAO.addRegularLesson(regularLesson);
    }

    @Override
    @Transactional
    public void updateRegularLesson(RegularLesson regularLesson) {
        regularLessonDAO.updateRegularLesson(regularLesson);
    }

    @Override
    @Transactional
    public List<RegularLesson> getFutureRegularLessons() {
        return regularLessonDAO.getFutureRegularLessons();
    }

    @Override
    @Transactional
    public void removeRegularLesson(RegularLesson regularLesson) {
        regularLessonDAO.removeRegularLesson(regularLesson);
    }

    @Override
    @Transactional
    public RegularLesson getRegularLessonById(long id) {
        return regularLessonDAO.getRegularLessonById(id);
    }
}

package org.exadel.training.dao;

import org.exadel.training.model.LessonEdit;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LessonEditDAOImpl implements LessonEditDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void addEdit(LessonEdit lessonEdit) {
        sessionFactory.getCurrentSession().persist(lessonEdit);
    }

    @Override
    public void updateEdit(LessonEdit lessonEdit) {
        sessionFactory.getCurrentSession().update(lessonEdit);
    }

    @Override
    public LessonEdit getEditByLessonIfExist(long lessonId) {
        return (LessonEdit) sessionFactory.getCurrentSession().createCriteria(LessonEdit.class)
                .add(Restrictions.eq("lesson.lessonId", lessonId))
                .add(Restrictions.or(
                        Restrictions.eq("isApproved", false),
                        Restrictions.isNull("isApproved")
                ))
                .uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LessonEdit> getAllEditsByLesson(long lessonId) {
        return sessionFactory.getCurrentSession().createCriteria(LessonEdit.class)
                .add(Restrictions.eq("lesson.lessonId", lessonId))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }
}

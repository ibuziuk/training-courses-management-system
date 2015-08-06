package org.exadel.training.dao;

import org.exadel.training.model.UploadFile;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

@Repository
public class UploadFileDAOImpl implements UploadFileDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addUploadFile(UploadFile uploadFile) {
        if (uploadFile != null) {
            sessionFactory.getCurrentSession().persist(uploadFile);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UploadFile> getUploadFilesByTraining(long trainingId) {
        Collection result = new LinkedHashSet(sessionFactory.getCurrentSession().createCriteria(UploadFile.class)
                .add(Restrictions.eq("training.trainingId", trainingId)).list());
        return new ArrayList<>(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public UploadFile getUploadFile(long uploadFileId) {
        return (UploadFile) sessionFactory.getCurrentSession().get(UploadFile.class, uploadFileId);
    }

    @Override
    public void removeUploadFile(UploadFile uploadFile) {
        if (uploadFile != null) {
            sessionFactory.getCurrentSession().delete(uploadFile);
        }
    }
}

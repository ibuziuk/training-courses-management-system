package org.exadel.training.dao;

import org.exadel.training.model.UploadFile;

import java.util.List;

public interface UploadFileDAO {
    void addUploadFile(UploadFile uploadFile);

    List<UploadFile> getUploadFilesByTraining(long trainingId);

    UploadFile getUploadFile(long uploadFileId);

    void removeUploadFile(UploadFile uploadFile);
}

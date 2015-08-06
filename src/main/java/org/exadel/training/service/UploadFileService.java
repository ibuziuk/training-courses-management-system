package org.exadel.training.service;


import org.exadel.training.model.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UploadFileService {
    long addUploadFile(long trainingId, MultipartFile file);

    List<UploadFile> getUploadFilesByTraining(long trainingId);

    void getUploadFile(long uploadFileId, HttpServletResponse response);

    UploadFile getUploadFile(long uploadFileId);

    void removeUploadFile(UploadFile uploadFile);
}
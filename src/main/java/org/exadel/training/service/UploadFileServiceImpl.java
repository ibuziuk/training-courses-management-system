package org.exadel.training.service;

import org.exadel.training.dao.UploadFileDAO;
import org.exadel.training.model.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class UploadFileServiceImpl implements UploadFileService {

    @Autowired
    private UploadFileDAO uploadFileDAO;

    @Autowired
    private TrainingService trainingService;

    @Override
    @Transactional
    public long addUploadFile(long trainingId, MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                File dir = new File(System.getProperty("user.home") + File.separator + "Uploads" + File.separator + trainingId);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                String path = dir.getAbsolutePath() + File.separator;
                int index = file.getOriginalFilename().lastIndexOf('.');
                String fileName = file.getOriginalFilename().substring(0, index);
                String fileExtension = file.getOriginalFilename().substring(index);
                File serverFile = new File(path + file.getOriginalFilename());
                int number = 1;
                while (serverFile.exists()) {
                    serverFile = new File(path + fileName + '(' + number + ')' + fileExtension);
                    number++;
                }
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                UploadFile uploadFile = new UploadFile();
                uploadFile.setName(serverFile.getName());
                uploadFile.setTraining(trainingService.getTrainingById(trainingId));
                uploadFileDAO.addUploadFile(uploadFile);
                return uploadFile.getUploadId();
            } catch (Exception e) {

            }
        } else {

        }
        return -1;
    }

    @Override
    @Transactional
    public List<UploadFile> getUploadFilesByTraining(long trainingId) {
        return uploadFileDAO.getUploadFilesByTraining(trainingId);
    }

    @Override
    @Transactional
    public void getUploadFile(long uploadFileId, HttpServletResponse response) {
        try {
            UploadFile uploadFile = uploadFileDAO.getUploadFile(uploadFileId);
            File dir = new File(System.getProperty("user.home") + "\\Uploads\\" + uploadFile.getTraining().getTrainingId());

            File serverFile = new File(dir.getAbsolutePath() + File.separator + uploadFile.getName());
            if (serverFile.exists()) {
                String fileName = "attachment; filename=";
                fileName += uploadFile.getName();
                if (serverFile.exists()) {
                    response.setContentLength(new Long(serverFile.length()).intValue());
                    response.setHeader("Content-Disposition", fileName);
                    FileCopyUtils.copy(new FileInputStream(serverFile), response.getOutputStream());
                }
                response.flushBuffer();
            }
        } catch (Exception e) {
        }
    }

    @Override
    @Transactional
    public UploadFile getUploadFile(long uploadFileId) {
        return uploadFileDAO.getUploadFile(uploadFileId);
    }

    @Override
    @Transactional
    public void removeUploadFile(UploadFile uploadFile) {
        uploadFileDAO.removeUploadFile(uploadFile);
    }
}

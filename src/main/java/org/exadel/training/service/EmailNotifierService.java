package org.exadel.training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class EmailNotifierService {
    private final String MAIL_FROM = "exadel.trainings.system@gmail.com";
    private final String IMAGE_RESOURCE_NAME = "exadel-logo.png";
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private ServletContext servletContext;

    public void sendEmailNotification(final String to[], final String subject, final Context context) {
        File file = new File(servletContext.getRealPath("").toString() + "resources/vendors/pics/exadel-logo.png");
        byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
        }
        final byte[] imageBytes = bytes;
        context.setVariable("imageResourceName", IMAGE_RESOURCE_NAME);
        final String htmlContent = templateEngine.process("mail", context);
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                message.setTo(to);
                message.setFrom(MAIL_FROM);
                message.setSubject(subject);
                message.setText(htmlContent, true);
                final InputStreamSource imageSource = new ByteArrayResource(imageBytes);
                message.addInline(IMAGE_RESOURCE_NAME, imageSource, "image/png");
            }
        };
        mailSender.send(preparator);
    }
}

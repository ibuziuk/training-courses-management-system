package org.exadel.training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service
public class EmailNotifierService {
    private final String MAIL_FROM = "exadel.trainings.system@gmail.com";
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;

    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send_email_notification(final String to[], final String subject, final String notification_text) {
        final Context ctx = new Context();
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("textFromInput", notification_text);
        final String htmlContent = templateEngine.process("mail", ctx);
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true,"UTF-8");
                message.setTo(to);
                message.setFrom(MAIL_FROM);
                message.setSubject(subject);
                message.setText(htmlContent, true);
            }
        };
        mailSender.send(preparator);
    }
}

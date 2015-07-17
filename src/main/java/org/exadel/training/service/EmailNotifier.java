//package com.alexey_hw.notifications;
//
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.mail.javamail.MimeMessagePreparator;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
//
//import javax.mail.internet.MimeMessage;
//import java.util.Date;
//
///**
// * Created by Alex on 09.07.2015.
// */
//public class EmailNotifier {
//    private final String MAIL_FROM = "exadel.trainings.system@gmail.com";
//    private JavaMailSender mailSender;
//    private TemplateEngine templateEngine;
//
//    public void setTemplateEngine(TemplateEngine templateEngine) {
//        this.templateEngine = templateEngine;
//    }
//
//    public void setMailSender(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//
//    public void send_email_notification(final String to[], final String subject, final String notification_text) {
//        final Context ctx = new Context();
//        //creating context for template
//        ctx.setVariable("subscriptionDate", new Date());
//        ctx.setVariable("textFromInput", notification_text);
//        final String htmlContent = templateEngine.process("mail.html", ctx);
//        MimeMessagePreparator preparator = new MimeMessagePreparator() {
//            public void prepare(MimeMessage mimeMessage) throws Exception {
//                MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true,"UTF-8");
//                message.setTo(to);
//                message.setFrom(MAIL_FROM);
//                message.setSubject(subject);
//                message.setText(htmlContent, true);
//            }
//        };
//        mailSender.send(preparator);
//    }
//}

package by.issoft.kholodok.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * Created by dmitrykholodok on 5/6/18
 */

@Component
public class EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    public void send(String from, String[] to, String subject, String body) throws MessagingException {
        send(from, to, subject, body, null);
    }

    public void send(String from, String[] to, String subject, String body, File file) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        if (file != null) {
            helper.addAttachment(file.getName(), file);
        }

        javaMailSender.send(message);
    }

}

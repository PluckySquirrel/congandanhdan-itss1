package vn.edu.hust.soict.japango.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import vn.edu.hust.soict.japango.common.enums.Mail;
import vn.edu.hust.soict.japango.common.utils.StringUtils;
import vn.edu.hust.soict.japango.service.MailService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;

    private final String templateResourcePath = "templates/mail";

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendMail(String toEmail, Mail mail, Map<String, String> args) {
        try {
            String mailContent = readTemplate(templateResourcePath + "/" + mail.getTemplateFile());
            mailContent = applyArguments(mailContent, args);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(mail.getSubject());
            helper.setText(mailContent, true);

            mailSender.send(message);
            log.info("Sent mail '{}' to {}", mail.getSubject(), toEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String readTemplate(String templateFilePath) {
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(templateFilePath);

            if (inputStream == null) {
                throw new FileNotFoundException(templateFilePath);
            } else {
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
                reader.close();
                return sb.toString();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private String applyArguments(String mailTemplate, Map<String, String> args) {
        for (var arg : args.entrySet()) {
            mailTemplate = StringUtils.replaceArg(mailTemplate, arg.getKey(), arg.getValue());
        }
        return mailTemplate;
    }
}

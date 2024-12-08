package vn.edu.hust.soict.japango.service;

import vn.edu.hust.soict.japango.common.enums.Mail;

import java.util.Map;

public interface MailService {
    void sendMail(String toEmail, Mail mail, Map<String, String> args);
}

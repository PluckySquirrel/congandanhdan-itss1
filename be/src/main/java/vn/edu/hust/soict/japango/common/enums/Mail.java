package vn.edu.hust.soict.japango.common.enums;

import lombok.Getter;

@Getter
public enum Mail {
    RESET_PASSWORD_MAIL("JapanGo - パスワードリセット", "reset_password_mail.html");

    private final String subject;
    private final String templateFile;

    Mail(String subject, String templateFile) {
        this.subject = subject;
        this.templateFile = templateFile;
    }
}

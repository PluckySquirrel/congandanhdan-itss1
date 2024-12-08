package vn.edu.hust.soict.japango.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class AppConfig {
    @Bean
    public DateTimeFormatter defaultDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");
    }
}

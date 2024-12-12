package vn.edu.hust.soict.japango.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Configuration
public class AppConfig {
    @Value("${app.default-time-zone}")
    private String defaultTimeZone;

    @Bean
    public DateTimeFormatter defaultDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");
    }

    @Bean
    public CommandLineRunner initialize() {
        return args -> {
            TimeZone.setDefault(TimeZone.getTimeZone(defaultTimeZone));
        };
    }
}

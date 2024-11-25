package vn.edu.hust.soict.japango;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JapangoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JapangoApplication.class, args);
	}

}

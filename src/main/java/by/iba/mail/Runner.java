package by.iba.mail;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Runner{

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Runner.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}

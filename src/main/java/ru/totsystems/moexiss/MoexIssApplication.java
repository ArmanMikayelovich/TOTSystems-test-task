package ru.totsystems.moexiss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.totsystems.moexiss.repository.SecurityRepository;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableWebMvc
public class MoexIssApplication  {

    @Autowired
    SecurityRepository securityRepository;
    public static void main(String[] args) {

        SpringApplication.run(MoexIssApplication.class, args);
    }
}

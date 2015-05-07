package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class BootstrapAutoConfigureApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootstrapAutoConfigureApplication.class, args);
	}

}

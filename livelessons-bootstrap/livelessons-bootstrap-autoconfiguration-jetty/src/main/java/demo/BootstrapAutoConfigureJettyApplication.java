package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class BootstrapAutoConfigureJettyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootstrapAutoConfigureJettyApplication.class, args);
	}

}

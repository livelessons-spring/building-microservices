package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(MyProperties.class)
public class Application {

	public Application(MyProperties properties) {
		System.out.println(properties.getAddress());
		System.out.println(properties.getPort());
		System.out.println(properties.getType());
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

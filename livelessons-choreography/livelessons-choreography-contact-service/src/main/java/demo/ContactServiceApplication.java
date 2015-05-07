package demo;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class ContactServiceApplication {

	@Bean
	public CommandLineRunner init(ContactRepository contactRepository) {
		return args -> {
			contactRepository.deleteAll();
			Arrays.asList("pwebb,jlong".split(",")).forEach(userId -> {
				Arrays.asList(
						"Stéphane,Maldini;Dave,Syer;Juergen,Hoeller;Rob,Winch;Mark,Fisher;Arjen,Poutsma"
								.split(";"))
						.stream().map(name -> name.split(","))
						.map(firstLastName -> new Contact(userId, firstLastName[0],
								firstLastName[1],
								firstLastName[0].toLowerCase() + "@email.com", "friend"))
						.forEach(contactRepository::save);
			});
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(ContactServiceApplication.class, args);
	}

}

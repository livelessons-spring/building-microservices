package demo;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Josh Long
 * @author Dave Syer (THANK YOU DAVE!)
 */
@SpringBootApplication
@EnableResourceServer
@RestController
public class SsoResourceApplication {

	@RequestMapping("/hi")
	public Map<String, Object> hi(Principal principal) {
		System.out.println("received request from " + principal.getName());
		Map<String, Object> result = new HashMap<>();
		result.put("id", UUID.randomUUID().toString());
		result.put("content", "Hello, world!");
		return result;
	}

	public static void main(String[] args) {
		SpringApplication.run(SsoResourceApplication.class, args);
	}

}

package demo;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Order(2)
@Component
public class RestTemplateExample implements CommandLineRunner {

	private final RestTemplate restTemplate;

	public RestTemplateExample(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public void run(String... strings) throws Exception {
		// use the "smart" Eureka-aware RestTemplate
		ParameterizedTypeReference<List<Bookmark>> responseType = new ParameterizedTypeReference<List<Bookmark>>() {
		};

		ResponseEntity<List<Bookmark>> exchange = this.restTemplate.exchange(
				"http://bookmark-service/{userId}/bookmarks", HttpMethod.GET, null,
				responseType, (Object) "pwebb");

		exchange.getBody().forEach(System.out::println);
	}

}

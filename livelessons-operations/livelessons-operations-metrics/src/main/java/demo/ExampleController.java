package demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

	private final ExampleService service;

	public ExampleController(ExampleService service) {
		this.service = service;
	}

	@RequestMapping("/")
	public String hello() {
		this.service.call();
		return "Hello World!";
	}

}

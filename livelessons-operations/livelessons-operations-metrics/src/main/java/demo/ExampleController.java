package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

	private final ExampleService service;

	@Autowired
	public ExampleController(ExampleService service) {
		this.service = service;
	}

	@RequestMapping("/")
	public String hello() {
		this.service.call();
		return "Hello World!";
	}

}

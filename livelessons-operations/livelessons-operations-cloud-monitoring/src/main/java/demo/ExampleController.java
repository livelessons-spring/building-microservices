package demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

	private final ExampleHealthIndicator indicator;

	public ExampleController(ExampleHealthIndicator indicator) {
		this.indicator = indicator;
	}

	@GetMapping("/")
	public String hello() {
		return "Hello World!";
	}

	@GetMapping("/up")
	public String up() {
		this.indicator.setUp(true);
		return "now up";
	}

	@GetMapping("/down")
	public String down() {
		this.indicator.setUp(false);
		return "now down";
	}

}

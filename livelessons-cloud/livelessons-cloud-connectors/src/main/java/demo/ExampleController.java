package demo;

import org.springframework.cloud.app.ApplicationInstanceInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

	private final JdbcTemplate jdbc;

	private final ApplicationInstanceInfo info;

	public ExampleController(JdbcTemplate jdbc, ApplicationInstanceInfo info) {
		this.jdbc = jdbc;
		this.info = info;
	}

	@RequestMapping("/")
	public String hello() {
		return this.jdbc.queryForObject("select model from car where id = 1",
				String.class);
	}

	@RequestMapping("/cloudinfo")
	public ApplicationInstanceInfo info() {
		return this.info;
	}

}

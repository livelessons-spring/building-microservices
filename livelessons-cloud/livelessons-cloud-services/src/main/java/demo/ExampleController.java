package demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

	private final JdbcTemplate jdbc;

	public ExampleController(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@RequestMapping("/")
	public String hello() {
		return jdbc.queryForObject("select model from car where id = 1", String.class);
	}

}

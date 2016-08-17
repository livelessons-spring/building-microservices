package basic;

import java.sql.ResultSet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Demonstrates HTTP basic authentication in action. Try to access the resource
 * <A href="http://localhost:8080/hi">in your browser</A> and you'll be prompted to supply
 * a username and password. You can find some sample usernames and passwords in
 * {@code data.sql}. Similarly, if you try to {@code curl http://localhost:8080/hi},
 * you'll be prompted for authentication information.
 */
@SpringBootApplication
public class BasicSecurityApplication {

	@Bean
	public UserDetailsService userDetailsService(JdbcTemplate jdbcTemplate) {
		// @formatter:off
		RowMapper<User> userRowMapper = (ResultSet rs, int i) -> new User(
				rs.getString("ACCOUNT_NAME"),
				rs.getString("PASSWORD"),
				rs.getBoolean("ENABLED"),
				rs.getBoolean("ENABLED"),
				rs.getBoolean("ENABLED"),
				rs.getBoolean("ENABLED"),
				AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN"));
		return username -> jdbcTemplate.queryForObject(
				"select * from ACCOUNT where ACCOUNT_NAME = ?", userRowMapper, username);
		// @formatter:on
	}

	public static void main(String[] args) {
		SpringApplication.run(BasicSecurityApplication.class, args);
	}

}

package demo;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// https://spring.io/blog/2015/02/03/sso-with-oauth2-angular-js-and-spring-security-part-v

/**
 * Easy to retrieve an access token using:
 * {@code curl -X POST -vu acme:acmesecret http://localhost:9999/uaa/oauth/token -H "Accept: application/json" -d "password=spring&username=jlong&grant_type=password&scope=openid&client_secret=acmesecret&client_id=acme" }
 *
 * Then, send the access token to an OAuth2 secured REST resource using:
 * {@code curl http://localhost:9000/hi -H "Authorization: Bearer _INSERT TOKEN_"}
 *
 * @author Dave Syer (THANK YOU DAVE!)
 * @author Josh Long
 */
@SpringBootApplication
@EnableResourceServer
@RestController
public class SsoAuthServerApplication {

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	@Bean
	UserDetailsService userDetailsService(JdbcTemplate jdbcTemplate) {
		// @formatter:off
		RowMapper<User> userDetailsRowMapper = (rs, i) -> new User(
				rs.getString("ACCOUNT_NAME"),
				rs.getString("PASSWORD"),
				rs.getBoolean("ENABLED"),
				rs.getBoolean("ENABLED"),
				rs.getBoolean("ENABLED"),
				rs.getBoolean("ENABLED"),
				AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN"));
		return username -> jdbcTemplate.queryForObject(
				"select * from ACCOUNT where ACCOUNT_NAME = ?", userDetailsRowMapper,
				username);
		// @formatter:on
	}

	public static void main(String[] args) {
		SpringApplication.run(SsoAuthServerApplication.class, args);
	}

}

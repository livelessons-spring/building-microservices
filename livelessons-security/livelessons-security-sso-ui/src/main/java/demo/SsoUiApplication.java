package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.security.oauth2.sso.EnableOAuth2Sso;

/**
 * @author Josh Long
 * @author Dave Syer
 */
@SpringBootApplication
@EnableZuulProxy
@EnableOAuth2Sso
public class SsoUiApplication {

	// THANK YOU DAVE!

	public static void main(String[] args) {
		SpringApplication.run(SsoUiApplication.class, args);
	}

}

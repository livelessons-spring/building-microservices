package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This example is lifted <a href=
 * "https://github.com/SpringOne2GX-2014/microservice-security/blob/master/certs/pom.xml"
 * > from Dr. Dave Syer's example on microservice security</a>.
 */
@SpringBootApplication
public class BasicHttpsSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicHttpsSecurityApplication.class, args);
	}

}

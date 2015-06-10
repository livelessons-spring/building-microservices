package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

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

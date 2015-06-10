package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void configureGlobal( AuthenticationManagerBuilder auth,
                                 UserDetailsService userDetailsService) throws Exception {
        auth.userDetailsService( userDetailsService);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        RowMapper<User> userRowMapper = (rs, i) -> new User(
                rs.getString("ACCOUNT_NAME"),
                rs.getString("PASSWORD"),
                rs.getBoolean("ENABLED"),
                rs.getBoolean("ENABLED"),
                rs.getBoolean("ENABLED"),
                rs.getBoolean("ENABLED"),
                AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN"));
        return username -> jdbcTemplate.queryForObject("select * from ACCOUNT where ACCOUNT_NAME = ?", userRowMapper, username);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                .anyRequest().authenticated()
                .and().
                x509()
                .and().
                sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER).and()
                .csrf()
                .disable();
    }

}

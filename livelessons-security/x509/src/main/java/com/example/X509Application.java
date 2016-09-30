package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@SpringBootApplication
@EnableWebSecurity
public class X509Application extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().anyRequest().authenticated()
                .and().x509()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and().csrf().disable();

    }

    @Bean
    CommandLineRunner data(AccountRepository accountRepository) {
        return args -> Stream.of("pwebb,boot", "rod,atomist", "dsyer,cloud", "jlong,spring")
                .map(x -> x.split(","))
                .forEach(t -> accountRepository.save(new Account(t[0], t[1], true)));
    }

    public static void main(String[] args) {
        SpringApplication.run(X509Application.class, args);
    }
}


@RestController
class GreetingsRestController {

    @RequestMapping(method = RequestMethod.GET, value = "/hi")
    Map<String, String> greet(Principal p) {
        return Collections.singletonMap("greeting", "Hello, " + p.getName());
    }
}

@Service
class AccountUserDetailsService implements UserDetailsService {

    @Autowired
    public AccountUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return accountRepository.findByUsername(s)
                .map(account -> new User(account.getUsername(),
                        account.getPassword(),
                        account.isActive(),
                        account.isActive(),
                        account.isActive(),
                        account.isActive(),
                        AuthorityUtils.createAuthorityList("ROLE_USER")
                ))
                .orElseThrow(() -> new UsernameNotFoundException("couldn't find the user!"));
    }

    private final AccountRepository accountRepository;

}

interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
}

@Entity
class Account {

    @Id
    @GeneratedValue
    private Long id;
    private String username, password;
    private boolean active;

    Account() {
    }

    public Account(String username, String password, boolean active) {
        this.username = username;
        this.password = password;
        this.active = active;
    }

    public Long getId() {

        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return active;
    }
}
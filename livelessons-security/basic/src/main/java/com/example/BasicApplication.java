package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
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
public class BasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicApplication.class, args);
    }
}

@Component
class SampleDataCLR implements CommandLineRunner {

    private final AccountRepository accountRepository;

    @Autowired
    public SampleDataCLR(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Stream.of("pwebb,boot", "dsyer,cloud", "jlong,spring", "rod,atomist")
                .map(x -> x.split(","))
                .forEach(tuple -> accountRepository.save(new Account(tuple[0], tuple[1], true)));
    }
}

@RestController
class GreetingsRestController {

    @RequestMapping(method = RequestMethod.GET, value = "/hi")
    public Map<String, String> greetings(Principal p) {
        return Collections.singletonMap("content", "Hello, " + p.getName());
    }
}

@Service
class AccountUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.accountRepository.findByUsername(username)
                .map(account -> new User(account.getUsername(),
                        account.getPassword(),
                        account.isActive(), account.isActive(), account.isActive(), account.isActive(),
                        AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER")
                ))
                .orElseThrow(() -> new UsernameNotFoundException("couldn't find " + username + "!"));
    }
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

    public Account() {// why JPA why?
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
package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@EnableDiscoveryClient
@SpringBootApplication
public class GreetingsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreetingsServiceApplication.class, args);
    }
}

@RestController
class GreetingsRestController {

    @RequestMapping(method = RequestMethod.GET, value = "/greetings/{name}")
    Map<String, String> greeting(@PathVariable String name,
                                 @RequestHeader("x-forwarded-host") Optional<String> host,
                                 @RequestHeader("x-forwarded-port") Optional<Integer> port) {
        host.ifPresent(h -> System.out.println("host = " + h));
        port.ifPresent(p -> System.out.println("port = " + p));
        return Collections.singletonMap("greeting", "Hello, " + name + "!");
    }
}
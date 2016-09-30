package demo ;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableCircuitBreaker
@EnableFeignClients
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class GreetingsClientApplication {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(GreetingsClientApplication.class, args);
    }
}

@FeignClient("greetings-service")
interface GreetingsClient {

    @RequestMapping(method = RequestMethod.GET, value = "/greetings/{name}")
    Greeting greet(@PathVariable("name") String name);
}

@RestController
class GreetingsApiGatewayRestController {

    private final GreetingsClient greetingsClient;

    @Autowired
    public GreetingsApiGatewayRestController(GreetingsClient client) {
        this.greetingsClient = client;
    }

    public String fallback(String name) {
        return "ohai!";
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping(method = RequestMethod.GET, value = "/hi/{name}")
    String greet(@PathVariable String name) {
        return this.greetingsClient.greet(name).getGreeting();
    }
}

class Greeting {
    private String greeting;

    public String getGreeting() {
        return greeting;
    }
}

//@Component
class RateLimitingZuulFilter extends ZuulFilter {

    private final RateLimiter rateLimiter = RateLimiter.create(1.0 / 30.0);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 100;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        try {
            RequestContext currentContext = RequestContext.getCurrentContext();
            HttpServletResponse response = currentContext.getResponse();

            if (!this.rateLimiter.tryAcquire()) {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.getWriter().append(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());
                currentContext.setSendZuulResponse(false);
            }
        } catch (IOException e) {
            ReflectionUtils.rethrowRuntimeException(e);
        }
        return null;
    }
}
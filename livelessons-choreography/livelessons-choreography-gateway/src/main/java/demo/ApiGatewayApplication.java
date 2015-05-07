package demo;

import reactor.Environment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * Visit <a href="http://localhost:9023/jlong/passport">the passport integration API</a>
 * and the Zuul proxied API gateways for
 * <a href="http://localhost:9023/bookmark-service/jlong/bookmarks/">bookmarks</a> and
 * <a href="http://localhost:9023/contact-service/jlong/contacts/">contacts</a>.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableZuulProxy
public class ApiGatewayApplication {

	@Bean
	public Environment env() {
		return Environment.initializeIfEmpty();
	}

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}

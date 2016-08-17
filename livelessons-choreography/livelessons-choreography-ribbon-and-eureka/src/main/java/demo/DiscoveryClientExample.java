package demo;

import org.apache.commons.lang.builder.ToStringBuilder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class DiscoveryClientExample implements CommandLineRunner {

	private final DiscoveryClient discoveryClient;

	public DiscoveryClientExample(DiscoveryClient discoveryClient) {
		this.discoveryClient = discoveryClient;
	}

	@Override
	public void run(String... strings) throws Exception {
		this.discoveryClient.getInstances("contact-service")
				.forEach((ServiceInstance s) -> {
					System.out.println(ToStringBuilder.reflectionToString(s));
				});

		this.discoveryClient.getInstances("bookmark-service")
				.forEach((ServiceInstance s) -> {
					System.out.println(ToStringBuilder.reflectionToString(s));
				});
	}

}

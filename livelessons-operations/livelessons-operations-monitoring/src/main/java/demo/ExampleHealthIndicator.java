package demo;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class ExampleHealthIndicator implements HealthIndicator {

	@Override
	public Health health() {
		if ((int) (System.currentTimeMillis() / 1000) % 2 == 0) {
			return Health.outOfService().withDetail("time", "is running out").build();
		}
		return Health.up().build();
	}

}

package demo;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.stereotype.Component;

@Component
public class ExamplePublicMetrics implements PublicMetrics {

	private final Random random = new Random();

	@Override
	public Collection<Metric<?>> metrics() {
		return Arrays.asList(new Metric<>("example.public", random.nextInt()));
	}

}

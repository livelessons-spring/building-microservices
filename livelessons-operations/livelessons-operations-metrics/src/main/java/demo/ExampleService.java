package demo;

import java.util.Random;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {

	private final Random random = new Random();

	private final CounterService counterService;

	private final GaugeService gaugeService;

	public ExampleService(CounterService counterService, GaugeService gaugeService) {
		this.counterService = counterService;
		this.gaugeService = gaugeService;
	}

	public void call() {
		this.counterService.increment("example.counter");
		this.gaugeService.submit("example.gauge", this.random.nextDouble());
	}

}

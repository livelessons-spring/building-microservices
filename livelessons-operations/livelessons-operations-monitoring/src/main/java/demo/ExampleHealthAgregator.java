package demo;

import org.springframework.boot.actuate.health.OrderedHealthAggregator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class ExampleHealthAgregator extends OrderedHealthAggregator {

	public ExampleHealthAgregator() {
		setStatusOrder(Status.OUT_OF_SERVICE, Status.DOWN, Status.UP, Status.UNKNOWN);
	}

}

package demo;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class ExampleHealthIndicator extends AbstractHealthIndicator {

	private boolean up = true;

	public void setUp(boolean up) {
		this.up = up;
	}

	@Override
	protected void doHealthCheck(Builder builder) throws Exception {
		builder.status(this.up ? Status.UP : Status.DOWN);
	}

}

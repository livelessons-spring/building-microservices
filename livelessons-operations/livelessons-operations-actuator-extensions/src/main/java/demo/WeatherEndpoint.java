package demo;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.stereotype.Component;

@Component
public class WeatherEndpoint extends AbstractEndpoint<String> {

	public WeatherEndpoint() {
		super("weather");
	}

	@Override
	public String invoke() {
		return "frightful";
	}

}

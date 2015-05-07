package demo;

import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
public class WeatherMvcEndpoint extends EndpointMvcAdapter {

	public WeatherMvcEndpoint() {
		super(new WeatherEndpoint());
	}

	@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String invokeHtml() {
		return "<h1 style=\"color:red\">" + getDelegate().invoke() + "</h1>";
	}

}

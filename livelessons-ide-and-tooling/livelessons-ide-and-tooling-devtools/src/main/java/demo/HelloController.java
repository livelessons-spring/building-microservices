package demo;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

	private static final String[] INDEX_PROPERTIES = { "CF_INSTANCE_INDEX",
			"INSTANCE_INDEX" };

	private final Environment environment;

	@Autowired
	public HelloController(Environment environment) {
		this.environment = environment;
	}

	@RequestMapping("/")
	public ModelAndView hello() {
		ModelAndView modelAndView = new ModelAndView("hello");
		modelAndView.addObject("helloMessage", "Spring Boot");
		modelAndView.addObject("index", getIndex());
		modelAndView.addObject("uptime", getUpTime());
		return modelAndView;
	}

	private int getIndex() {
		for (String property : INDEX_PROPERTIES) {
			if (this.environment.containsProperty(property)) {
				return this.environment.getProperty(property, Integer.class);
			}
		}
		return -1;
	}

	private String getUpTime() {
		long uptime = ManagementFactory.getRuntimeMXBean().getUptime();
		long minutes = TimeUnit.MILLISECONDS.toMinutes(uptime);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(uptime);
		return String.format("%d min, %d sec", minutes,
				seconds - TimeUnit.MINUTES.toSeconds(minutes));
	}

}

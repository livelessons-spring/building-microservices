package demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ProjectNameRestController {

	@Value("${configuration.projectName}")
	private String projectName;

	@RequestMapping("/project-name")
	public String projectName() {
		return this.projectName;
	}

}

package demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

@Configuration
public class SimpleRepositoryRestMvcConfiguration extends RepositoryRestMvcConfiguration {

	@Override
	protected void configureRepositoryRestConfiguration(
			RepositoryRestConfiguration config) {
		config.exposeIdsFor(Person.class);
	}

}

package demo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

@Component
public class PersonResourceProcessor implements ResourceProcessor<Resource<Person>> {

	@Override
	public Resource<Person> process(Resource<Person> resource) {
		String id = Long.toString(resource.getContent().getId());
		UriComponents uriComponents = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/people/{id}/photo").buildAndExpand(id);
		String uri = uriComponents.toUriString();
		resource.add(new Link(uri, "photo"));
		return resource;
	}

}

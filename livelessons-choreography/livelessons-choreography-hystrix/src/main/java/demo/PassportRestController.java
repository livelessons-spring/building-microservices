package demo;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PassportRestController {

	private final IntegrationClient integrationClient;

	public PassportRestController(IntegrationClient integrationClient) {
		this.integrationClient = integrationClient;
	}

	@RequestMapping("/{userId}/passport")
	Passport passport(@PathVariable String userId) {
		return new Passport(userId, this.integrationClient.getContacts(userId),
				this.integrationClient.getBookmarks(userId));
	}

}

package demo;

import java.util.Arrays;
import java.util.Collection;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.stereotype.Component;

@Component
class IntegrationClient {

	private final ContactClient contactClient;

	private final BookmarkClient bookmarkClient;

	public IntegrationClient(ContactClient contactClient, BookmarkClient bookmarkClient) {
		this.contactClient = contactClient;
		this.bookmarkClient = bookmarkClient;
	}

	public Collection<Bookmark> getBookmarksFallback(String userId) {
		System.out.println("getBookmarksFallback");
		return Arrays.asList();
	}

	@HystrixCommand(fallbackMethod = "getBookmarksFallback")
	public Collection<Bookmark> getBookmarks(String userId) {
		return this.bookmarkClient.getBookmarks(userId);
	}

	public Collection<Contact> getContactsFallback(String userId) {
		System.out.println("getContactsFallback");
		return Arrays.asList();
	}

	@HystrixCommand(fallbackMethod = "getContactsFallback")
	public Collection<Contact> getContacts(String userId) {
		return this.contactClient.getContacts(userId);
	}

}

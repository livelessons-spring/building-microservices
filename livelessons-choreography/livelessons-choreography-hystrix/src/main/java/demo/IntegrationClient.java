package demo;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
class IntegrationClient {

	@Autowired
	private ContactClient contactClient;

	@Autowired
	private BookmarkClient bookmarkClient;

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
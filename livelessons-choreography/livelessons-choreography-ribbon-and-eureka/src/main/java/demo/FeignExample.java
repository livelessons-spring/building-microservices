package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(3)
@Component
public class FeignExample implements CommandLineRunner {

	@Autowired
	private ContactClient contactClient;

	@Autowired
	private BookmarkClient bookmarkClient;

	@Override
	public void run(String... strings) throws Exception {
		this.bookmarkClient.getBookmarks("jlong").forEach(System.out::println);
		this.contactClient.getContacts("jlong").forEach(System.out::println);
	}

}

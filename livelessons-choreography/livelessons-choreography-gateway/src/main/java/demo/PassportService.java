package demo;

import reactor.Environment;
import reactor.rx.Stream;
import reactor.rx.Streams;

import org.springframework.stereotype.Component;

@Component
public class PassportService {

	private final Environment environment;

	private final ContactClient contactClient;

	private final BookmarkClient bookmarkClient;

	public PassportService(Environment environment, ContactClient contactClient,
			BookmarkClient bookmarkClient) {
		this.environment = environment;
		this.contactClient = contactClient;
		this.bookmarkClient = bookmarkClient;
	}

	public Stream<Bookmark> getBookmarks(String userId) {
		return Streams.<Bookmark>create(subscriber -> {
			this.bookmarkClient.getBookmarks(userId).forEach(subscriber::onNext);
			subscriber.onComplete();
		}).dispatchOn(this.environment, Environment.cachedDispatcher()).log("bookmarks");
	}

	public Stream<Contact> getContacts(String userId) {
		return Streams.<Contact>create(subscriber -> {
			this.contactClient.getContacts(userId).forEach(subscriber::onNext);
			subscriber.onComplete();
		}).dispatchOn(this.environment, Environment.cachedDispatcher()).log("contacts");
	}

	public Stream<Passport> getPassport(String userId, Stream<Contact> contacts,
			Stream<Bookmark> bookmarks) {
		return Streams.zip(contacts.buffer(), bookmarks.buffer(),
				tuple -> new Passport(userId, tuple.getT1(), tuple.getT2()));
	}

}

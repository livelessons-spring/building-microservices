package demo;

import java.util.Collection;

class Passport {

	private String userId;

	private Collection<Bookmark> bookmarks;

	private Collection<Contact> contacts;

	public Passport(String userId, Collection<Contact> contacts,
			Collection<Bookmark> bookmarks) {
		this.userId = userId;
		this.contacts = contacts;
		this.bookmarks = bookmarks;
	}

	public String getUserId() {
		return userId;
	}

	public Collection<Contact> getContacts() {
		return contacts;
	}

	public Collection<Bookmark> getBookmarks() {
		return bookmarks;
	}

	@Override
	public String toString() {
		return "Passport{" + "userId='" + userId + '\'' + ", bookmarks=" + bookmarks
				+ ", contacts=" + contacts + '}';
	}

}

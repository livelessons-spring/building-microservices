package demo;

import java.util.Collection;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{userId}/bookmarks")
public class BookmarkRestController {

	private final BookmarkRepository bookmarkRepository;

	public BookmarkRestController(BookmarkRepository bookmarkRepository) {
		this.bookmarkRepository = bookmarkRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Collection<Bookmark> getBookmarks(@PathVariable String userId) {
		return this.bookmarkRepository.findByUserId(userId);
	}

	@RequestMapping(value = "/{bookmarkId}", method = RequestMethod.GET)
	public Bookmark getBookmark(@PathVariable String userId,
			@PathVariable Long bookmarkId) {
		return this.bookmarkRepository.findByUserIdAndId(userId, bookmarkId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Bookmark createBookmark(@PathVariable String userId,
			@RequestBody Bookmark bookmark) {
		Bookmark bookmarkInstance = new Bookmark(userId, bookmark.getHref(),
				bookmark.getDescription(), bookmark.getLabel());
		return this.bookmarkRepository.save(bookmarkInstance);
	}

}

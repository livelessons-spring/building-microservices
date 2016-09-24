package demo;

import reactor.rx.Stream;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class PassportRestController {

	private final PassportService passportService;

	public PassportRestController(PassportService passportService) {
		this.passportService = passportService;
	}

	@RequestMapping("/{userId}/passport")
	public DeferredResult<Passport> passport(@PathVariable String userId) {
		DeferredResult<Passport> passportDeferredResult = new DeferredResult<>();
		Stream<Bookmark> bookmarkStream = this.passportService.getBookmarks(userId);
		Stream<Contact> contactStream = this.passportService.getContacts(userId);
		this.passportService.getPassport(userId, contactStream, bookmarkStream)
				.consume(passportDeferredResult::setResult);
		return passportDeferredResult;
	}

}

package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import reactor.rx.Stream;

@RestController
public class PassportRestController {

	@Autowired
	private PassportService passportService;

	@RequestMapping("/{userId}/passport")
	public DeferredResult<Passport> passport(@PathVariable String userId) {
		DeferredResult<Passport> passportDeferredResult = new DeferredResult<>();
		Stream<Bookmark> bookmarkStream = this.passportService.getBookmarks(userId);
		Stream<Contact> contactStream = this.passportService.getContacts(userId);
		this.passportService.getPassport(userId, contactStream, bookmarkStream).consume(
				passportDeferredResult::setResult);
		return passportDeferredResult;
	}

}

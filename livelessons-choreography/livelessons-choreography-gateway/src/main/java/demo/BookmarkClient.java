package demo;

import java.util.Collection;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("bookmark-service")
public interface BookmarkClient {

	@RequestMapping(method = RequestMethod.GET, value = "/{userId}/bookmarks")
	public Collection<Bookmark> getBookmarks(@PathVariable("userId") String userId);

}

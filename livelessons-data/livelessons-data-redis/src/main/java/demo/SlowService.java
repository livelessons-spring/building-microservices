package demo;

import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SlowService {

	@Cacheable("slow")
	public String execute(String arg) {
		try {
			Thread.sleep(2000);
		}
		catch (InterruptedException ex) {
		}
		return UUID.randomUUID().toString();
	}

}

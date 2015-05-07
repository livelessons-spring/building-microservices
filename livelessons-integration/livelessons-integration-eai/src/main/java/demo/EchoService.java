package demo;

import org.springframework.stereotype.Service;

@Service
class EchoService {

	public String echo(String input) {
		return "Echo: " + input;
	}
}
@RestController
public class Example {

	@RequestMapping("/")
	String hello() {
		return "Hello World!"
	}

}

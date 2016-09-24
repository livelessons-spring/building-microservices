package demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/people/{id}/photo")
public class PersonPhotoRestController {

	private File root;

	private final PersonRepository personRepository;

	public PersonPhotoRestController(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Value("${user.home}")
	void setUserHome(String home) {
		this.root = new File(home, "Desktop/images");
		Assert.isTrue(this.root.exists() || this.root.mkdirs(),
				"The path '" + this.root.getAbsolutePath() + "' must exist.");
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Resource> read(@PathVariable Long id) throws Exception {
		Person person = this.findOne(id);
		File file = fileFor(person);
		if (!file.exists()) {
			throw new FileNotFoundException(file.getAbsolutePath());
		}
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<>(new FileSystemResource(file), httpHeaders,
				HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
	public ResponseEntity<?> write(@PathVariable Long id,
			@RequestParam MultipartFile file) throws Exception {
		Person person = this.findOne(id);
		FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(fileFor(person)));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(id)
				.toUri();
		return ResponseEntity.created(location).build();
	}

	private Person findOne(Long id) {
		Person person = this.personRepository.findOne(id);
		if (person == null) {
			throw new PersonNotFoundException(id);
		}
		return person;
	}

	private File fileFor(Person person) {
		return new File(this.root, Long.toString(person.getId()));
	}

}

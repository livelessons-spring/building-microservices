package demo;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DataRestApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void getCar() throws Exception {
		ResponseEntity<String> entity = this.restTemplate.getForEntity("/cars/1",
				String.class);
		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(entity.getBody(), containsString("Civic"));
	}

	@Test
	public void findCars() throws Exception {
		ResponseEntity<String> entity = this.restTemplate
				.getForEntity("/cars/search/find?make=honda", String.class);
		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(entity.getBody(), containsString("cars/1"));
		assertThat(entity.getBody(), containsString("cars/2"));
		assertThat(entity.getBody(), not(containsString("cars/3")));
	}

}

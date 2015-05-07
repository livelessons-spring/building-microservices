package demo;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DataRestApplication.class)
@WebIntegrationTest(randomPort = true)
public class DataRestApplicationTests {

	@Value("${local.server.port}")
	private int port;

	@Test
	public void getCar() throws Exception {
		String url = "http://localhost:" + this.port + "/cars/1";
		ResponseEntity<String> entity = new TestRestTemplate().getForEntity(
				url, String.class);
		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(entity.getBody(), containsString("Civic"));
	}

	@Test
	public void findCars() throws Exception {
		String url = "http://localhost:" + this.port
				+ "/cars/search/find?make=honda";
		ResponseEntity<String> entity = new TestRestTemplate().getForEntity(
				url, String.class);
		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(entity.getBody(), containsString("cars/1"));
		assertThat(entity.getBody(), containsString("cars/2"));
		assertThat(entity.getBody(), not(containsString("cars/3")));
	}

}

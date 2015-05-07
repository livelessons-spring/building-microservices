package basic;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BasicSecurityApplication.class)
@WebIntegrationTest(randomPort = true)
public class BasicSecurityApplicationTests {

	@Value("${local.server.port}")
	private int port;

	@Autowired
	private RestTemplate restTemplate;

	@Test
	public void contextLoaded() throws Throwable {
		ResponseEntity<String> re = restTemplate.getForEntity(
				"http://localhost:" + port + "/hi", String.class);
		String actual = re.getBody().trim().toLowerCase();
		Assert.assertTrue(actual.contains("hello"));
		System.out.println("received: " + actual);
	}

	@Configuration
	public static class BasicAuthRestTemplateConfig {

		private String user = "pwebb", password = "boot";

		@Bean
		RestTemplate auth() {
			// credentials
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
					this.user, this.password);

			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			credsProvider.setCredentials(AuthScope.ANY, credentials);

			// set credentialsProvider on httpClient
			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

			httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
			CloseableHttpClient httpClient = httpClientBuilder.build();

			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(
					httpClient);

			RestTemplate restTemplate = new RestTemplate();
			restTemplate.setRequestFactory(factory);

			return restTemplate;
		}
	}

}

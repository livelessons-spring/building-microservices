package demo;

import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.ResourceAccessException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BasicHttpsSecurityApplicationTests {

	private SSLContext defaultContext;

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Before
	public void setUp() throws Exception {
		this.defaultContext = SSLContext.getDefault();
	}

	@After
	public void reset() throws Exception {
		SSLContext.setDefault(this.defaultContext);
	}

	@Test(expected = ResourceAccessException.class)
	public void testUnauthenticatedHello() throws Exception {
		ResponseEntity<String> httpsEntity = this.restTemplate.getForEntity("/hi",
				String.class);
		assertThat(httpsEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(httpsEntity.getBody()).containsIgnoringCase("hello, world");
	}

	@Test
	public void testAuthenticatedHello() throws Exception {
		TestRestTemplate restTemplate = new TestRestTemplate();
		restTemplate.getRestTemplate()
				.setRequestFactory(new HttpComponentsClientHttpRequestFactory(HttpClients
						.custom().setSSLSocketFactory(socketFactory()).build()));
		ResponseEntity<String> httpsEntity = restTemplate
				.getForEntity("https://localhost:" + this.port + "/hi", String.class);
		assertThat(httpsEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(httpsEntity.getBody()).containsIgnoringCase("hello, rod");
	}

	private SSLConnectionSocketFactory socketFactory() throws Exception {
		char[] password = "password".toCharArray();
		KeyStore truststore = KeyStore.getInstance("PKCS12");
		truststore.load(new ClassPathResource("rod.p12").getInputStream(), password);
		SSLContextBuilder builder = new SSLContextBuilder();
		builder.loadKeyMaterial(truststore, password);
		builder.loadTrustMaterial(truststore, new TrustSelfSignedStrategy());
		return new SSLConnectionSocketFactory(builder.build(),
				new NoopHostnameVerifier());
	}

}

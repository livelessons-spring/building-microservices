package demo;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BasicHttpsSecurityApplication.class)
@WebIntegrationTest(randomPort = true)
public class BasicHttpsSecurityApplicationTest {

    @Autowired
    private RestTemplate template;

    @Value("${local.server.port}")
    private String port;

    @Configuration
    public static class RestClientConfiguration {

        HttpClient httpClient() throws Exception {
            return HttpClients.custom().setSSLSocketFactory(socketFactory())
                    .build();
        }

        SSLConnectionSocketFactory socketFactory() throws Exception {
            char[] password = "password".toCharArray();
            KeyStore truststore = KeyStore.getInstance("PKCS12");
            truststore.load(getKeyStoreFile(), password);
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadKeyMaterial(truststore, password);
            builder.loadTrustMaterial(truststore, new TrustSelfSignedStrategy());
            return new SSLConnectionSocketFactory(builder.build(),
                    new AllowAllHostnameVerifier());
        }

        InputStream getKeyStoreFile() throws IOException {
            ClassPathResource resource = new ClassPathResource("rod.p12");
            return resource.getInputStream();
        }

        @Bean
        RestTemplate authenticatedRestTemplate() throws Exception {
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(
                    httpClient());
            return new RestTemplate(factory);
        }
    }

    private SSLContext defaultContext;

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
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> httpsEntity = template.getForEntity(
                "https://localhost:" + this.port + "/hi", String.class);
        Assert.assertEquals(HttpStatus.OK, httpsEntity.getStatusCode());
        Assert.assertTrue(httpsEntity.getBody().toLowerCase()
                .contains("hello, world"));
    }

    @Test
    public void testAuthenticatedHello() throws Exception {

        ResponseEntity<String> httpsEntity = template.getForEntity(
                "https://localhost:" + this.port + "/hi", String.class);
        Assert.assertEquals(HttpStatus.OK, httpsEntity.getStatusCode());
        Assert.assertTrue(httpsEntity.getBody().toLowerCase()
                .contains("hello, world"));
    }


}

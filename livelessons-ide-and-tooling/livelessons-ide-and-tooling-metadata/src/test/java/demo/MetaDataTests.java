
package demo;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

import org.junit.Test;

import org.springframework.boot.configurationmetadata.ConfigurationMetadataProperty;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataRepository;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataRepositoryJsonBuilder;

public class MetaDataTests {

	private static final Charset UTF_8 = Charset.forName("UTF-8");

	@Test
	public void writeMetadataInfo() throws Exception {
		InputStream inputStream = new FileInputStream(
				"target/classes/META-INF/spring-configuration-metadata.json");
		ConfigurationMetadataRepository repository = ConfigurationMetadataRepositoryJsonBuilder
				.create(UTF_8).withJsonResource(inputStream).build();

		for (Map.Entry<String, ConfigurationMetadataProperty> entry : repository
				.getAllProperties().entrySet()) {
			System.out.println(
					entry.getKey() + " = " + entry.getValue().getShortDescription());
		}
	}

}

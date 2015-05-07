package demo;

import java.io.IOException;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.MetricRegistry;
import org.coursera.metrics.datadog.DatadogReporter;
import org.coursera.metrics.datadog.DatadogReporter.Expansion;
import org.coursera.metrics.datadog.transport.HttpTransport;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OperationsCloudMetricsApplication {

	@Value("${datadog.apikey}")
	private String apiKey;

	@Bean
	public DatadogReporter datadogReporter(MetricRegistry registry) throws IOException {
		EnumSet<Expansion> expansions = EnumSet.of(Expansion.COUNT,
				Expansion.RATE_1_MINUTE, Expansion.RATE_15_MINUTE, Expansion.MEDIAN,
				Expansion.P95, Expansion.P99);
		HttpTransport httpTransport = new HttpTransport.Builder().withApiKey(this.apiKey)
				.build();
		DatadogReporter reporter = DatadogReporter.forRegistry(registry)
				.withHost("livelessons").withTransport(httpTransport)
				.withExpansions(expansions).build();
		reporter.start(10, TimeUnit.SECONDS);
		return reporter;
	}

	public static void main(String[] args) {
		SpringApplication.run(OperationsCloudMetricsApplication.class, args);
	}

}

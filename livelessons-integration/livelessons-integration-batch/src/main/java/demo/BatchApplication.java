package demo;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This demonstrates the Spring Batch Java configuration DSL. Spring Batch automatically
 * runs jobs that are in the context for you (the poster example for the
 * {@link org.springframework.boot.CommandLineRunner}. Spring Batch emits events (via the
 * usual Spring {@link org.springframework.context.ApplicationEventPublisher} mechanism)
 * when a Spring Batch {@link org.springframework.batch.core.Job} is finished executing.
 */
@SpringBootApplication
@EnableBatchProcessing
public class BatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}

}

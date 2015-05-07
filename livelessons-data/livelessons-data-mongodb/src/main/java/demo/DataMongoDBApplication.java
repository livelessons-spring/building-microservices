package demo;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.util.StreamUtils;

@SpringBootApplication
public class DataMongoDBApplication {

	private static final Point PIVOTAL_OFFICE = new Point(-122.403664, 37.781825);
	private static final Point TREASURE_ISLAND = new Point(-122.370834, 37.820490);
	private static final Point EMERYVILLE = new Point(-122.287063, 37.834998);
	private static final Point FERRY_BUILDING = new Point(-122.391777, 37.790787);

	private static final Distance FIVE_MILES = new Distance(5, Metrics.MILES);

	@Bean
	public InitializingBean seedDatabase(CarRepository repository) {
		return () -> {
			repository.deleteAll();
			repository.save(new Car("Honda", "Civic", 1997, EMERYVILLE));
			repository.save(new Car("Honda", "Accord", 2003, TREASURE_ISLAND));
			repository.save(new Car("Ford", "Escort", 1985, PIVOTAL_OFFICE));
		};
	}

	@Bean
	public CommandLineRunner exampleQuery(CarRepository repository) {
		return (args) -> {
			repository.findByMakeIgnoringCase("HONDA").forEach(System.err::println);

			repository.findByPositionNear(FERRY_BUILDING, FIVE_MILES)
					.forEach(System.err::println);
			;
		};
	}

	@Bean
	CommandLineRunner exampleGridFs(GridFsTemplate fs) {
		return (args) -> {
			// Write
			fs.store(new ByteArrayInputStream("a-picture-of-car".getBytes()), "pic1");
			// Read
			InputStream stream = fs.getResource("pic1").getInputStream();
			System.err
					.println(StreamUtils.copyToString(stream, Charset.defaultCharset()));
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(DataMongoDBApplication.class, args);
	}

}

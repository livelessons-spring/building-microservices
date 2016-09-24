package demo;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.boot.autoconfigure.batch.JobExecutionEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Configuration
public class ContactBatchJobConfiguration {

	@Bean
	public ItemProcessor<Contact, Contact> processor() {
		return (person) -> new Contact(person.getFirstName(), person.getLastName(),
				person.getEmail().toLowerCase());
	}

	@Bean
	public ItemReader<Contact> reader() {
		FlatFileItemReader<Contact> reader = new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("data.csv"));
		reader.setLineMapper(new DefaultLineMapper<Contact>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "firstName", "lastName", "email" });
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<Contact>() {
					{
						setTargetType(Contact.class);
					}
				});
			}
		});
		return reader;
	}

	@Bean
	public ItemWriter<Contact> writer(DataSource dataSource) {
		JdbcBatchItemWriter<Contact> writer = new JdbcBatchItemWriter<>();
		writer.setItemSqlParameterSourceProvider(
				new BeanPropertyItemSqlParameterSourceProvider<>());
		writer.setSql("INSERT INTO contact (first_name, last_name, email) "
				+ "VALUES (:firstName, :lastName, :email)");
		writer.setDataSource(dataSource);
		return writer;
	}

	@Bean
	public Job importUserJob(JobBuilderFactory jobs, Step s1) {
		return jobs.get("importUserJob").incrementer(new RunIdIncrementer()).flow(s1)
				.end().build();
	}

	@Bean
	public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Contact> reader,
			ItemWriter<Contact> writer, ItemProcessor<Contact, Contact> processor) {
		return stepBuilderFactory.get("step1").<Contact, Contact>chunk(10).reader(reader)
				.processor(processor).writer(writer).build();
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Component
	public static class BatchJobFinishedListener
			implements ApplicationListener<JobExecutionEvent> {

		private final JdbcTemplate jdbcTemplate;

		public BatchJobFinishedListener(JdbcTemplate jdbcTemplate) {
			this.jdbcTemplate = jdbcTemplate;
		}

		@Override
		public void onApplicationEvent(JobExecutionEvent event) {
			System.out.println("finished " + event.getJobExecution().toString());
			this.jdbcTemplate
					.query("SELECT first_name, last_name, email FROM contact",
							(rs, i) -> new Contact(rs.getString("first_name"),
									rs.getString("last_name"), rs.getString("email")))
					.forEach(System.out::println);
		}

	}

}

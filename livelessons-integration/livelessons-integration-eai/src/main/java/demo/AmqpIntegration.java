package demo;

import java.util.Arrays;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;

/**
 * this takes all inbound AMQP messages, passes them through the echo service and returns.
 */
@Configuration
public class AmqpIntegration {

	@Value("${echo.amqp.queue:echo}")
	private String echoQueueAndExchangeName;

	@Bean
	public InitializingBean prepareQueues(AmqpAdmin amqpAdmin) {
		return () -> {
			Queue queue = new Queue(this.echoQueueAndExchangeName, true);
			DirectExchange exchange = new DirectExchange(this.echoQueueAndExchangeName);
			Binding binding = BindingBuilder.bind(queue).to(exchange)
					.with(this.echoQueueAndExchangeName);
			amqpAdmin.declareQueue(queue);
			amqpAdmin.declareExchange(exchange);
			amqpAdmin.declareBinding(binding);
		};
	}

	@Bean
	public IntegrationFlow amqpReplyFlow(ConnectionFactory rabbitConnectionFactory,
			EchoService echoService) {
		return IntegrationFlows
				.from(Amqp.inboundGateway(rabbitConnectionFactory,
						this.echoQueueAndExchangeName))
				.transform(String.class, echoService::echo).get();
	}

	@Bean
	public CommandLineRunner client(RabbitTemplate template) {
		return args -> Arrays.asList("Josh", "Phil")
				.forEach(n -> System.out.println("template response: " + template
						.convertSendAndReceive(this.echoQueueAndExchangeName, n)));
	}
}

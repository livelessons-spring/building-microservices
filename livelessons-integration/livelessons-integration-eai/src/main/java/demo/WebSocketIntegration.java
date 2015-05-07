package demo;

import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowDefinition;
import org.springframework.integration.dsl.support.Function;
import org.springframework.integration.websocket.ServerWebSocketContainer;
import org.springframework.integration.websocket.outbound.WebSocketOutboundMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This sets up a Web Socket flow that takes incoming messages (against {@code /hi} REST
 * endpoint) and then {@link demo.EchoService#echo(String)} s them to all listening
 * clients (from {@code /}.
 *
 * Thanks to Artem Bilan for help in getting the websocket example working!
 */
@Configuration
@RestController
public class WebSocketIntegration {

	@Bean
	public ServerWebSocketContainer serverWebSocketContainer() {
		return new ServerWebSocketContainer("/messages").withSockJs();
	}

	@Bean
	public MessageHandler webSocketOutboundAdapter() {
		return new WebSocketOutboundMessageHandler(serverWebSocketContainer());
	}

	@Bean(name = "webSocketFlow.input")
	public MessageChannel requestChannel() {
		return new DirectChannel();
	}

	@Bean
	public IntegrationFlow webSocketFlow(EchoService echoService) {
		return (IntegrationFlowDefinition<?> integrationFlowDefinition) -> {
			Function<String, Object> splitter = (String messagePayload) -> {

				// convert the payload
				String echoValue = echoService.echo(messagePayload);

				// for each of the active WS sessions,
				// build a Message destined for that session containing the
				// input message
				return serverWebSocketContainer().getSessions().keySet().stream()
						.map(s -> MessageBuilder.withPayload(echoValue)
								.setHeader(SimpMessageHeaderAccessor.SESSION_ID_HEADER, s)
								.build())
						.collect(Collectors.toList());
			};

			integrationFlowDefinition.split(String.class, splitter)
					.channel(c -> c.executor(Executors.newCachedThreadPool()))
					.handle(webSocketOutboundAdapter());
		};
	}

	@RequestMapping("/echo")
	public void send(@RequestParam String name) {
		requestChannel().send(MessageBuilder.withPayload(name).build());
	}

}

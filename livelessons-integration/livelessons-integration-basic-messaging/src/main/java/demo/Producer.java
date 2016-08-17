package demo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Producer implements CommandLineRunner {

	private final RabbitMessagingTemplate messagingTemplate;

	public Producer(RabbitMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	@Override
	public void run(String... args) throws Exception {
		Notification notification = new Notification(UUID.randomUUID().toString(),
				"Hello, world!", new Date());

		Map<String, Object> headers = new HashMap<>();
		headers.put("notification-id", notification.getId());

		this.messagingTemplate.convertAndSend(MessagingApplication.NOTIFICATIONS,
				notification, headers, message -> {
					System.out.println("sending " + message.getPayload().toString());
					return message;
				});
	}

}

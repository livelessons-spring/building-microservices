package demo;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

	@RabbitListener(queues = MessagingApplication.NOTIFICATIONS)
	public void onNotification(Message<Notification> notification) {
		System.out.println("received " + notification.toString());
		System.out.println("received payload " + notification.getPayload());
		System.out.println("received headers " + notification.getHeaders().toString());
	}

}

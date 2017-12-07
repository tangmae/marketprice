package marketprice.marketpriceapp.messagelistener;

import marketprice.marketpriceapp.receiver.Receiver;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class ImportProductListener {
	
	@Autowired
	private ConnectionFactory connectionFactory;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("${rabbit.server.queuename}")
	private String rabbitQueueName;
	
	@Autowired
	private Receiver receiver;
	
	@Bean
	public Queue queue() {
		return new Queue(rabbitQueueName, false);
	}
	
	@Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(rabbitQueueName);
    }
	
	@Bean
    TopicExchange exchange() {
        return new TopicExchange("spring-boot-exchange");
    }
	
	@Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(rabbitQueueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }
	
	@Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receive");
    }


}

package marketprice.marketpriceapp.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	@Value("${rabbitmq.server.host}")
	private String rabbitmqHost;
	
	@Value("${rabbitmq.server.port}")
	private String rabbitmqPort;
	
	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory cachingConnectionFactory =  new CachingConnectionFactory(rabbitmqHost);
		cachingConnectionFactory.setPort(Integer.parseInt(rabbitmqPort));
		return cachingConnectionFactory;
	}
	
	@Bean
	public AmqpAdmin amqpAdmin() {
		return new RabbitAdmin(connectionFactory());
	}
	
	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate rabbit = new RabbitTemplate(connectionFactory());
		rabbit.setMessageConverter(new Jackson2JsonMessageConverter());
		return rabbit;
	}
	
}

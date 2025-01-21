package ntcgoer.emailservice.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
@Getter
@ConfigurationProperties(prefix = "np-queue.raw-string-email")
public class RawStringEmailQueueConfiguration {
    private String emailTopicExchangeName;
    private String emailRawStringQueueName;
    private String emailRawStringRoutingKey;

    @Bean
    Queue emailRawStringQueue() {
        return new Queue(emailRawStringQueueName, false);
    }

    @Bean
    TopicExchange rawStringEmailExchange() {
        return new TopicExchange(emailTopicExchangeName);
    }

    @Bean
    Binding bindingEmailRawStringQueueName(Queue emailRawStringQueue, TopicExchange rawStringEmailExchange) {
        return BindingBuilder.bind(emailRawStringQueue).to(rawStringEmailExchange).with(emailRawStringRoutingKey);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rawEmailListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }
}

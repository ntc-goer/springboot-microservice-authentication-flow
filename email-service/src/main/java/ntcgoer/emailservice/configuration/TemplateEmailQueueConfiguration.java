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
@ConfigurationProperties(prefix = "np-queue.template-email")
public class TemplateEmailQueueConfiguration {
    private String emailTopicExchangeName;
    private String emailTemplateQueueName;
    private String emailTemplateRoutingKey;

    @Bean
    Queue emailTemplateQueue() {
        return new Queue(emailTemplateQueueName, false);
    }

    @Bean
    TopicExchange templateEmailExchange() {
        return new TopicExchange(emailTopicExchangeName);
    }

    @Bean
    Binding bindingEmailTemplateQueueName(Queue emailTemplateQueue, TopicExchange templateEmailExchange) {
        return BindingBuilder.bind(emailTemplateQueue).to(templateEmailExchange).with(emailTemplateRoutingKey);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory templateEmailListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }
}

package ntcgoer.emailservice.reiceiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import ntcgoer.emailservice.code.EmailTemplate;
import ntcgoer.emailservice.model.TemplateEmailMessageModel;
import ntcgoer.emailservice.service.impl.EmailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@Component
@AllArgsConstructor
public class TemplateEmailReceiver {
    private static final Logger logger = LoggerFactory.getLogger(TemplateEmailReceiver.class);

    private final ObjectMapper objectMapper;
    EmailServiceImpl emailServiceImpl;

    @RabbitListener(queues = "#{templateEmailQueueConfiguration.emailTemplateQueueName}")
    public void receiveTemplateEmailMessage(
            String msg,
            @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
            Channel channel
    ) throws Exception {
        try {
            TemplateEmailMessageModel templateEmailMessageModel = this.objectMapper.readValue(msg, TemplateEmailMessageModel.class);
            Context context = new Context();
            for (String key : templateEmailMessageModel.getData().keySet()) {
                context.setVariable(key, templateEmailMessageModel.getData().get(key));
            }
            String template = EmailTemplate.valueOf(templateEmailMessageModel.getTemplate()).getTemplate();
            this.emailServiceImpl.sendTemplateMessage(templateEmailMessageModel.getToEmail(), templateEmailMessageModel.getSubject(), template, context);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            logger.error(String.format("TemplateEmailReceiver.sendTemplateEmail: %s", e.getMessage()));
            channel.basicNack(deliveryTag, false, true);
            e.printStackTrace();
        }
    }
}

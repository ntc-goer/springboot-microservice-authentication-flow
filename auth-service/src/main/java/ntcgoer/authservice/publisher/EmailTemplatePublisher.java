package ntcgoer.authservice.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import ntcgoer.authservice.client.queueclient.QueueClient;
import ntcgoer.authservice.client.queueclient.queueclientrequest.TemplateEmailQueueClientRequest;
import ntcgoer.authservice.code.OutboxMessageStatus;
import ntcgoer.authservice.code.OutboxMessageType;
import ntcgoer.authservice.entity.OutboxMessageEntity;
import ntcgoer.authservice.publisher.emailtemplatepublisherdata.EmailTemplatePublisherPayload;
import ntcgoer.authservice.repository.OutboxMessageRepository;
import ntcgoer.sharingmodule.configuration.TemplateEmailQueueConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmailTemplatePublisher {
    private static final Logger logger = LoggerFactory.getLogger(EmailTemplatePublisher.class);
    private final ObjectMapper objectMapper;

    private final OutboxMessageRepository outboxMessageRepository;
    private final QueueClient queueClient;
    private final TemplateEmailQueueConfiguration templateEmailQueueConfiguration;

    @Scheduled(fixedRateString = "${np-schedule.email-template-outbox.fixed-rate}")
    public void publish() throws JsonProcessingException {
            List<OutboxMessageEntity> outboxMessageEntityList = outboxMessageRepository.findAllByMessageTypeAndStatusOrderByCreatedAtAsc(
                    OutboxMessageType.EMAIL_TEMPLATE,
                    OutboxMessageStatus.PENDING
            );
            if (outboxMessageEntityList.isEmpty()) {
                logger.info("EmailTemplatePublisher.publish: No data found");
                return;
            }
            for (OutboxMessageEntity outboxMessageEntity : outboxMessageEntityList) {
                EmailTemplatePublisherPayload templatePublisherPayload = this.objectMapper.readValue(
                        outboxMessageEntity.getPayload(),
                        EmailTemplatePublisherPayload.class
                );

                TemplateEmailQueueClientRequest templateEmailQueueClientRequest = TemplateEmailQueueClientRequest
                        .builder()
                        .subject(templatePublisherPayload.getMetadata().getSubject())
                        .toEmail(templatePublisherPayload.getMetadata().getEmailTo())
                        .template(templatePublisherPayload.getMetadata().getTemplate())
                        .data(templatePublisherPayload.getVariable())
                        .build();
                queueClient.PublishMessage(
                        templateEmailQueueConfiguration.getEmailTopicExchangeName(),
                        templateEmailQueueConfiguration.getEmailTemplateRoutingKey(),
                        new ObjectMapper().writeValueAsString(templateEmailQueueClientRequest)
                );
                outboxMessageEntity.setStatus(OutboxMessageStatus.PROCESSED);
                outboxMessageRepository.save(outboxMessageEntity);
            }
    }
}

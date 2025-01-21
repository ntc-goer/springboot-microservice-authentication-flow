package ntcgoer.authservice.repository;

import ntcgoer.authservice.code.OutboxMessageStatus;
import ntcgoer.authservice.code.OutboxMessageType;
import ntcgoer.authservice.entity.OutboxMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutboxMessageRepository extends JpaRepository<OutboxMessageEntity, String> {
    List<OutboxMessageEntity> findAllByMessageTypeAndStatusOrderByCreatedAtAsc(OutboxMessageType messageType, OutboxMessageStatus status);
}

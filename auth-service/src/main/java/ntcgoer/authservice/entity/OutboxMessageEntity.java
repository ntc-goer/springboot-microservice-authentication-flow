package ntcgoer.authservice.entity;

import jakarta.persistence.*;
import lombok.*;
import ntcgoer.authservice.code.OutboxMessageStatus;
import ntcgoer.authservice.code.OutboxMessageType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "\"outbox_message\"")
public class OutboxMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "\"id\"", nullable = false, updatable = false)
    private String id;

    @Column(name = "\"messageType\"",nullable = false, updatable = false)
    private OutboxMessageType messageType;

    @Column(name="\"status\"", nullable = false)
    private OutboxMessageStatus status;

    @Column(name="\"payload\"", columnDefinition = "TEXT")
    private String payload;

    @CreationTimestamp
    @Column(name = "\"createdAt\"")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "\"updatedAt\"")
    private LocalDateTime updatedAt;

    @Column(name = "\"deletedAt\"")
    private LocalDateTime deletedAt;
}

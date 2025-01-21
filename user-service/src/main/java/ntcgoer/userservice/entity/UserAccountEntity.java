package ntcgoer.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "\"userAccount\"")
public class UserAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "\"id\"", nullable = false, updatable = false)
    private String id;

    @Column(name= "\"userName\"", nullable = false)
    private String userName;

    @Column(name = "\"password\"", nullable = false)
    private String password;

    @Column(name = "\"email\"", nullable = false)
    private String email;

    @Column(name = "\"lastLoginAt\"")
    private LocalDateTime lastLoginAt;

    @Column(name = "\"verifiedAt\"")
    private LocalDateTime verifiedAt;

    @CreationTimestamp
    @Column(name = "\"createdAt\"")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "\"updatedAt\"")
    private LocalDateTime updatedAt;

    @Column(name = "\"deletedAt\"")
    private LocalDateTime deletedAt;
}

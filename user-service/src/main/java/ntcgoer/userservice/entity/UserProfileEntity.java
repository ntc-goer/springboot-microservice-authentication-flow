package ntcgoer.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntcgoer.userservice.code.GenderEnum;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "\"userProfile\"")
public class UserProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "\"id\"")
    private String id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "\"accountId\"", referencedColumnName = "\"id\"")
    private UserAccountEntity userAccount;

    @Column(name = "\"firstName\"")
    private String firstName;

    @Column(name = "\"lastName\"")
    private String lastName;

    @Column(name = "\"role\"")
    private String role;

    @Column(name = "\"status\"")
    private String status;

    @Column(name = "\"phoneNumber\"")
    private String phoneNumber;

    @Column(name = "\"gender\"")
    private GenderEnum gender;

    @Column(name = "\"profilePictureUrl\"")
    private String profilePictureUrl;

    @Column(name = "\"address\"")
    private String address;

    @Column(name = "\"birthday\"")
    private LocalDate birthday;

    @CreationTimestamp
    @Column(name = "\"createdAt\"")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "\"updatedAt\"")
    private LocalDateTime updatedAt;

    @Column(name = "\"deletedAt\"")
    private LocalDateTime deletedAt;
}

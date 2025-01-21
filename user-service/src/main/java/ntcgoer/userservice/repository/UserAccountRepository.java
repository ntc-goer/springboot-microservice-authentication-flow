package ntcgoer.userservice.repository;

import ntcgoer.userservice.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccountEntity, String> {
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    @Query("SELECT u FROM UserAccountEntity u WHERE u.userName = :emailUserName OR u.email = :emailUserName")
    UserAccountEntity findByEmailUserName(@Param("emailUserName") String emailOrUserName);
}

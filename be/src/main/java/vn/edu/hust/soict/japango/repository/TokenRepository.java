package vn.edu.hust.soict.japango.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hust.soict.japango.common.enums.TokenType;
import vn.edu.hust.soict.japango.entity.Token;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Token t WHERE t.userId = :userId AND t.type = :type AND t.expireTime > :time AND t.isUsed = false")
    int deleteByUserIdAndTypeAndExpireTimeAfterAndIsNotUsed(Long userId, TokenType type, LocalDateTime time);

    @Query("SELECT t FROM Token t WHERE t.value = :value AND t.expireTime > :time AND t.isUsed = false")
    Optional<Token> findByValueAndExpireTimeAfterAndIsNotUsed(String value, LocalDateTime time);
}

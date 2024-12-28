package vn.edu.hust.soict.japango.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hust.soict.japango.common.enums.ActionType;
import vn.edu.hust.soict.japango.entity.History;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    Page<History> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    Page<History> findByUserIdAndActionInAndCreatedAtBetween(Long userId, List<ActionType> actionTypes, LocalDateTime from, LocalDateTime to, Pageable pageable);
    Optional<History> findByUuid(String uuid);
    @Modifying
    @Transactional
    long deleteAllByUserId(Long userId);
}

package vn.edu.hust.soict.japango.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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
    @Query("SELECT h FROM History h " +
            "WHERE h.userId = :userId " +
            "AND h.action IN :actionTypes " +
            "AND h.createdAt BETWEEN :from AND :to " +
            "AND (LOWER(h.input) LIKE CONCAT('%', LOWER(:keyword), '%') " +
            "OR LOWER(h.output) LIKE CONCAT('%', LOWER(:keyword), '%')) " +
            "ORDER BY h.createdAt DESC")
    Page<History> findByFilters(Long userId, String keyword, List<ActionType> actionTypes, LocalDateTime from, LocalDateTime to, Pageable pageable);
    Optional<History> findByUuid(String uuid);
    @Modifying
    @Transactional
    long deleteAllByUserId(Long userId);
}

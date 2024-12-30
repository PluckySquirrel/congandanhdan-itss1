package vn.edu.hust.soict.japango.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hust.soict.japango.common.enums.SaveType;
import vn.edu.hust.soict.japango.entity.SavedResult;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedResultRepository extends JpaRepository<SavedResult, Long> {
    boolean existsByHistoryUuidAndSaveType(String uuid, SaveType saveType);

    @Modifying
    @Transactional
    void deleteByHistoryUuidAndSaveType(String uuid, SaveType saveType);

    Page<SavedResult> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    @Modifying
    @Transactional
    long deleteAllByUserId(Long userId);

    Optional<SavedResult> findByUuid(String uuid);

    @Query("SELECT s FROM SavedResult s " +
            "WHERE s.userId = :userId " +
            "AND (LOWER(s.input) LIKE CONCAT('%', LOWER(:keyword), '%') " +
            "OR LOWER(s.output) LIKE CONCAT('%', LOWER(:keyword), '%')) " +
            "ORDER BY s.createdAt DESC")
    Page<SavedResult> findByUserIdAndKeyword(Long userId, String keyword, Pageable pageable);

    List<SavedResult> findByUserId(Long userId);
}

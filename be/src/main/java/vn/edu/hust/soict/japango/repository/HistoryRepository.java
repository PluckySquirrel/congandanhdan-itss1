package vn.edu.hust.soict.japango.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hust.soict.japango.entity.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    Page<History> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}

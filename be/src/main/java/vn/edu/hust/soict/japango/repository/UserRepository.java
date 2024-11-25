package vn.edu.hust.soict.japango.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hust.soict.japango.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

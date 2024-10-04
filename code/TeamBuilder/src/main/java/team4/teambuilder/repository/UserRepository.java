package team4.teambuilder.repository;

import team4.teambuilder.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByGroupId(Long groupId);
}
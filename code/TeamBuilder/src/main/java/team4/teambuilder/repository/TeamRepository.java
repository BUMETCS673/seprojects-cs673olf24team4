package team4.teambuilder.repository;

import team4.teambuilder.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
    List<Team> findByGroupId(Long groupId);
    void deleteByGroupId(Long groupId);
}
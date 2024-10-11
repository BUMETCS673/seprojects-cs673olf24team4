package team4.teambuilder.service;

import team4.teambuilder.model.Team;
import team4.teambuilder.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public List<Team> getTeamsByGroupId(Long groupId) {
        return teamRepository.findByGroupId(groupId);
    }
}